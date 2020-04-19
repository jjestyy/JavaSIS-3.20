package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.data.QuestionRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnswerItemDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.FilterDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.JournalRowsRequestDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.QuestionsItemDTO;
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
    public List<QuestionsItemDTO> getQuestions(JournalRowsRequestDTO req) {
        PageRequest pageRequest = PageRequest.of(req.getPage()-1, req.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        return questionRepository.findByNameContainingIgnoreCase(req.getSearch(), pageRequest)
            .stream()
            .map(question -> new QuestionsItemDTO(question, answerRepository.findByQuestion(question)))
            .filter(questionsItemDto -> checkFilter(questionsItemDto, req.getFilters()))
            .collect(Collectors.toList());
    }

    @Override
    public QuestionsItemDTO createQuestion(QuestionsItemDTO dto) {
        return saveQuestionData(dto, new Question());
    }

    private QuestionsItemDTO saveQuestionData(QuestionsItemDTO dto, Question question) {
        question.setName(dto.getName());
        questionRepository.save(question);

        for (AnswerItemDTO answerDTO : dto.getAnswers()) {
            Answer answer = new Answer();
            answer.setName(answerDTO.getAnswerText());
            answer.setIsCorrect(answerDTO.getIsCorrect());
            answer.setQuestion(question);

            answerRepository.save(answer);
        }
        return new QuestionsItemDTO(question, answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionsItemDTO editQuestion(QuestionsItemDTO dto) {
        Question question = questionRepository.findById(Long.parseLong(dto.getId()))
                .orElseThrow(() -> new RuntimeException("Cannot find question with such id - " + dto.getId()));
        answerRepository.findByQuestion(question).forEach(answer -> answerRepository.delete(answer));
        return saveQuestionData(dto, question);
    }

    private boolean checkFilter(QuestionsItemDTO QuestionsItemDto, List<FilterDTO> filters) {
        for(FilterDTO filter: filters) {
            if(filter.getCode().equals("question-answer-count") &&
                    QuestionsItemDto.getAnswers().size() != Integer.parseInt(filter.getValue())) {
                return false;
            }
        }
        return true;
    }
}
