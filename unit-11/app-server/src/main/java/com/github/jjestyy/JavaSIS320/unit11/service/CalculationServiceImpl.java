package com.github.jjestyy.JavaSIS320.unit11.service;

import com.github.jjestyy.JavaSIS320.unit11.data.AnswerRepository;
import com.github.jjestyy.JavaSIS320.unit11.dto.AnsweredQuestionDTO;
import com.github.jjestyy.JavaSIS320.unit11.dto.SessionQuestionAnswerDTO;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CalculationServiceImpl implements CalculationService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Double getPoints(AnsweredQuestionDTO question) {
        List<SessionQuestionAnswerDTO> sessionAnswers = question.getAnswersList();
        int countOfCorrectAnswers = 0;
        int countOfWrongAnswers = 0;
        int countOfAllCorrectAnswers = 0;
        int countOfAllAnswers = 0;
        int countOfAllSelectedAnswers = 0;
        if(sessionAnswers != null && !sessionAnswers.isEmpty()) {
            countOfAllAnswers = sessionAnswers.size();
            for (SessionQuestionAnswerDTO answerDTO : sessionAnswers) {
                Answer answer = getAnswerByDTO(answerDTO);
                if(answerDTO.getIsSelected()){
                    countOfAllSelectedAnswers++;
                    if(answer.getIsCorrect()){
                        countOfAllCorrectAnswers++;
                        countOfCorrectAnswers++;
                    } else {
                        countOfWrongAnswers++;
                    }
                } else {
                    if(answer.getIsCorrect()) {
                        countOfAllCorrectAnswers++;
                    }
                }
            }
        }
        return calculatePointResult(countOfCorrectAnswers, countOfWrongAnswers,
                countOfAllCorrectAnswers, countOfAllAnswers, countOfAllSelectedAnswers);
    }

    private double calculatePointResult(double countOfCorrectAnswers, int countOfWrongAnswers,
                                        double countOfAllCorrectAnswers, int countOfAllAnswers,
                                        int countOfAllSelectedAnswers) {
        //no correct answers
        if(countOfAllCorrectAnswers == 0) {
            return countOfAllSelectedAnswers == 0 ? 1 : 0;
        } else {
            //all answers are correct
            if (countOfAllCorrectAnswers == countOfAllAnswers) {
                return countOfCorrectAnswers / countOfAllCorrectAnswers;
            } else {
                //default
                return Math.max(0, countOfCorrectAnswers / countOfAllCorrectAnswers -
                        countOfWrongAnswers / (countOfAllAnswers - countOfAllCorrectAnswers));
            }
        }
    }

    private Answer getAnswerByDTO(SessionQuestionAnswerDTO dto) {
        return answerRepository.findById(Long.parseLong(dto.getId()))
                .orElseThrow(() -> new RuntimeException(String.format("there is no answer with such id - %s", dto.getId())));
    }
}
