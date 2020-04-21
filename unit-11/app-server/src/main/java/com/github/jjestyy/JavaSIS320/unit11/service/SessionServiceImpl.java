package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SelectedAnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.SessionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.*;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
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

    private List<QuestionResult> questionsResultList;

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
    public void addSession(SessionDTO dto) {
        Session session = new Session();
        session.setFio(dto.getName());
        session.setPoints(calculatePoints(dto.getAnswers()));
        session.setInsertDate(LocalDate.now());
        sessionRepository.save(session);
        dto.getAnswers().forEach(selectedAnswerDTO -> createAnswer(selectedAnswerDTO, session));
    }

    private void createAnswer(SelectedAnswerDTO selectedAnswerDTO, Session session) {
        SelectedAnswer selectedAnswer = new SelectedAnswer();
        selectedAnswer.setAnswer(getAnswerByDTO(selectedAnswerDTO));
        selectedAnswer.setSession(session);
        selectedAnswerRepository.save(selectedAnswer);
    }

    private Answer getAnswerByDTO(SelectedAnswerDTO dto) {
        return answerRepository.findById(Long.parseLong(dto.getId()))
                .orElseThrow(() -> new RuntimeException(String.format("there is no answer with such id - %s", dto.getId())));
    }

    private void addQuestionResult(Answer answer, SelectedAnswerDTO selectedAnswerDTO) {
        Boolean isRight = answer.getIsCorrect() && selectedAnswerDTO.getIsSelected();
        Question question = answer.getQuestion();
        Optional<QuestionResult> questionResultRowOptional = questionsResultList
                .stream()
                .filter(questionResult -> questionResult.getQuestion().equals(question))
                .findFirst();
        QuestionResult questionResult;
        if(questionResultRowOptional.isPresent()) {
            questionResult = questionResultRowOptional.get();
        } else {
            List<Answer> answers = answerRepository.findByQuestion(question);
            questionResult = new QuestionResult(question, answers.size(), (int) answers.stream().filter(Answer::getIsCorrect).count());
            questionsResultList.add(questionResult);
        }
        questionResult.addAnswer(isRight);
    }

    private Double calculatePoints(List<SelectedAnswerDTO> answers) {
        answers.forEach(selectedAnswerDTO -> addQuestionResult(getAnswerByDTO(selectedAnswerDTO), selectedAnswerDTO));
        return questionsResultList
                .stream()
                .map((q) -> Math.max(0, (double) q.getCountOfCorrectAnswers() / q.getCountOfAllAnswers() -
                        (double) q.getCountOfWrongAnswers() / (q.getCountOfAllCorrectAnswers() - q.getCountOfAllAnswers())))
                .mapToDouble(Double::doubleValue).sum();

    }

}
