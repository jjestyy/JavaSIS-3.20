package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnswerItemDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.FilterDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDto;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDto;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<QuestionsItemDto> getQuestions(JournalRowsRequestDto req) {
        PageRequest pageRequest = PageRequest.of(Math.max(0, req.getPage()-1), req.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        return questionRepository.findByNameContainingIgnoreCase(req.getSearch(), pageRequest)
            .stream()
            .map(question -> new QuestionsItemDto(question, answerRepository.findByQuestion(question)))
            .filter(questionsItemDto -> checkFilter(questionsItemDto, req.getFilters()))
            .collect(Collectors.toList());
    }

    @Override
    public QuestionsItemDto createQuestion(QuestionsItemDto dto) {
        return saveQuestionData(dto, new Question());
    }

    private QuestionsItemDto saveQuestionData(QuestionsItemDto dto, Question question) {
        question.setName(dto.getName());
        questionRepository.save(question);

        for (AnswerItemDto answerDTO : dto.getAnswers()) {
            Answer answer = new Answer();
            answer.setName(answerDTO.getAnswerText());
            answer.setIsCorrect(answerDTO.getIsCorrect());
            answer.setQuestion(question);

            answerRepository.save(answer);
        }
        return new QuestionsItemDto(question, answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionsItemDto editQuestion(QuestionsItemDto dto) {
        Question question = getQuestionByDto(dto);
        answerRepository.findByQuestion(question).forEach(answer -> answerRepository.delete(answer));
        return saveQuestionData(dto, question);
    }

    private Question getQuestionByDto(QuestionsItemDto dto) {
        return questionRepository.findById(Long.parseLong(dto.getId()))
                .orElseThrow(() -> new RuntimeException("Cannot find question with such id - " + dto.getId()));
    }

    private boolean checkFilter(QuestionsItemDto QuestionsItemDto, List<FilterDto> filters) {
        for(FilterDto filter: filters) {
            if(filter.getCode().equals("question-answer-count") &&
                    QuestionsItemDto.getAnswers().size() != Integer.parseInt(filter.getValue())) {
                return false;
            }
        }
        return true;
    }
}
