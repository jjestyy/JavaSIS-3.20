package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SelectedAnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SessionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.*;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.SelectedAnswer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
        Double points = calculatePoints(dto.getQuestionsList());
        Session session = new Session();
        session.setFio(dto.getName());
        session.setPoints(points);
        session.setInsertDate(LocalDate.now());
        sessionRepository.save(session);
        dto.getQuestionsList()
                .forEach(answeredQuestionDTO ->
                        answeredQuestionDTO.getAnswersList()
                                .forEach(sessionQuestionAnswerDTO ->
                                        createAnswer(sessionQuestionAnswerDTO, session)));
        return points;
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

    private Double calculatePoints(List<AnsweredQuestionDTO> questions) {
        double resultPoints = (double) 0;
        for (AnsweredQuestionDTO answeredQuestionDTO : questions) {
            int countOfAllAnswers = answeredQuestionDTO.getAnswersList().size();
            int countOfCorrectAnswers = 0;
            int countOfAllCorrectAnswers = 0;
            int countOfWrongAnswers = 0;
            Iterator<SessionQuestionAnswerDTO> iterator = answeredQuestionDTO.getAnswersList().iterator();
            while (iterator.hasNext()){
                SessionQuestionAnswerDTO sessionQuestionAnswerDTO = iterator.next();
                Answer answer = getAnswerByDTO(sessionQuestionAnswerDTO);
                if(answer.getIsCorrect()) {
                    countOfAllCorrectAnswers++;
                    if(sessionQuestionAnswerDTO.getIsSelected()){
                        countOfCorrectAnswers++;
                    }
                } else {
                    if(sessionQuestionAnswerDTO.getIsSelected()){
                        countOfWrongAnswers++;
                    }
                }
                if(!iterator.hasNext()) {
                    resultPoints += (double) countOfCorrectAnswers / countOfAllAnswers -
                        (double) countOfWrongAnswers / (countOfAllCorrectAnswers - countOfAllAnswers);
                }
            }
        }
        return resultPoints / questions.size() * 100;
    }

}
