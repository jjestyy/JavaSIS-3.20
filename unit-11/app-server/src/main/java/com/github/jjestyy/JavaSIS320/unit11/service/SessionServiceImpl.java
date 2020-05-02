package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SelectedAnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SessionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.*;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.SelectedAnswer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Session;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SelectedAnswerRepository selectedAnswerRepository;

    @Autowired
    private CalculationService calculationService;

    @Override
    public List<QuestionsItemDTO> getRandomQuestionsList(int size) {
        List<QuestionsItemDTO> questionsItemDTOList = new ArrayList<>();
        List<Long> idsList = new ArrayList<>();

        questionRepository.findAll().forEach(question -> idsList.add(question.getId()));
        Collections.shuffle(idsList);

        questionRepository
                .findAllById(idsList.subList(0, Math.min(idsList.size(), size)))
                .forEach(question ->
                        questionsItemDTOList.add(
                                new QuestionsItemDTO(question,
                                        answerRepository.findByQuestion(question)
                                                        .stream()
                                                        .peek(answer -> answer.setIsCorrect(false))
                                                        .collect(Collectors.toList()))));
        return questionsItemDTOList;
    }

    @Override
    public double addSession(SessionDTO dto) {
        List<AnsweredQuestionDTO> questionsList = dto.getQuestionsList();
        double points = 0;
        double percent = 100;
        List<SessionQuestionAnswerDTO> answersToSave = new ArrayList<>();
        if(questionsList.size() > 0 ) {
            for (AnsweredQuestionDTO questionDTO : questionsList) {
                points += calculationService.getPoints(questionDTO);
                questionDTO.getAnswersList()
                        .stream()
                        .filter(SessionQuestionAnswerDTO::getIsSelected)
                        .forEach(answersToSave::add);
            }
            percent = points / questionsList.size() * 100;
        }
        Session session = createSession(dto, percent);
        answersToSave.forEach(sessionQuestionAnswerDTO -> createAnswer(sessionQuestionAnswerDTO, session));
        return percent;
    }

    @Override
    public List<SessionsItemDTO> getSessions(JournalRowsRequestDTO req) {
        PageRequest pageRequest = PageRequest.of(req.getPage()-1, req.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        return sessionRepository.findByFioContainingIgnoreCase(req.getSearch(), pageRequest)
                .stream()
                .map(SessionsItemDTO::new)
                .collect(Collectors.toList());
    }

    private Session createSession(SessionDTO dto, Double points) {
        Session session = new Session();
        session.setFio(dto.getName());
        session.setPoints(points);
        session.setInsertDate(LocalDate.now());
        sessionRepository.save(session);
        return session;
    }

    private void createAnswer(SessionQuestionAnswerDTO dto, Session session) {
        SelectedAnswer selectedAnswer = new SelectedAnswer();
        selectedAnswer.setAnswer(getAnswerByDTO(dto));
        selectedAnswer.setSession(session);
        selectedAnswerRepository.save(selectedAnswer);
    }

    private Answer getAnswerByDTO(SessionQuestionAnswerDTO dto) {
        return answerRepository.findById(Long.parseLong(dto.getId()))
                .orElseThrow(() -> new RuntimeException(String.format("there is no answer with such id - %s", dto.getId())));
    }


}
