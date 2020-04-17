package com.github.jjestyy.JavaSIS320.unit11;

import com.github.jjestyy.JavaSIS320.unit11.dto.*;
import com.github.jjestyy.JavaSIS320.unit11.entity.Answer;
import com.github.jjestyy.JavaSIS320.unit11.entity.Question;

import java.util.List;

public class TestData {

    public static List<Question> getTestQuestionsList() {
        Question question1 = new Question();
        question1.setName("Вопрос 1 ответ 1");
        Question question2 = new Question();
        question2.setName("Вопрос 2 ответ 1");
        Question question3 = new Question();
        question3.setName("Вопрос 3 ответ 1 и 3");
        Question question4 = new Question();
        question4.setName("Вопрос номер 4 ответ 2");
        return List.of(question1, question2, question3, question4);
    }

    public static List<Answer> getTestAnswersList(List<Question> questions) {

        Answer answer1 = new Answer();
        answer1.setName("ответ 1 от вопрос 1");
        answer1.setIsCorrect(true);
        answer1.setQuestion(questions.get(0));
        Answer answer2 = new Answer();
        answer2.setName("ответ 2 от вопрос 1");
        answer2.setIsCorrect(false);
        answer2.setQuestion(questions.get(0));
        Answer answer3 = new Answer();
        answer3.setName("ответ 3 от вопрос 1");
        answer3.setIsCorrect(false);
        answer3.setQuestion(questions.get(0));

        Answer answer4 = new Answer();
        answer4.setName("ответ 1 от вопрос 2");
        answer4.setIsCorrect(true);
        answer4.setQuestion(questions.get(1));
        Answer answer5 = new Answer();
        answer5.setName("ответ 2 от вопрос 2");
        answer5.setIsCorrect(false);
        answer5.setQuestion(questions.get(1));

        Answer answer6 = new Answer();
        answer6.setName("ответ 1 от вопрос 3");
        answer6.setIsCorrect(true);
        answer6.setQuestion(questions.get(2));
        Answer answer7 = new Answer();
        answer7.setName("ответ 2 от вопрос 3");
        answer7.setIsCorrect(false);
        answer7.setQuestion(questions.get(2));
        Answer answer8 = new Answer();
        answer8.setName("ответ 3 от вопрос 3");
        answer8.setIsCorrect(true);
        answer8.setQuestion(questions.get(2));

        Answer answer9 = new Answer();
        answer9.setName("ответ 1 от вопрос 1");
        answer9.setIsCorrect(true);
        answer9.setQuestion(questions.get(3));

        return List.of(answer1,answer2,answer3,answer4,answer5,answer6,answer7,answer8,answer9);
    }


    public static JournalRowsRequestDTO getJournalRowsRequestDTO(Boolean isPage, Boolean isSearch, Boolean isFilter) {
        JournalRowsRequestDTO journalRowsRequestDTO = new JournalRowsRequestDTO();
        if(isSearch) {
            journalRowsRequestDTO.setSearch("1");
        } else {
            journalRowsRequestDTO.setSearch("");
        }
        if(isPage) {
            journalRowsRequestDTO.setPageSize(2);
            journalRowsRequestDTO.setPage(1);
        } else {
            journalRowsRequestDTO.setPageSize(15);
            journalRowsRequestDTO.setPage(0);
        }
        if(isFilter) {
            FilterDto filterDto = new FilterDto();
            filterDto.setCode("question-answer-count");
            filterDto.setType("single-select");
            filterDto.setValue("2");
            journalRowsRequestDTO.setFilters(List.of(filterDto));
        } else {
            journalRowsRequestDTO.setFilters(List.of());
        }
        return journalRowsRequestDTO;
    }

    public static JournalRowsResultDTO getJournalRowsResultDTO() {
        List<QuestionsItemDto> questionsItemDtoList = getQuestionsItemDtos();
        return  new JournalRowsResultDTO(questionsItemDtoList.size(), questionsItemDtoList);
    }

    public static List<QuestionsItemDto> getQuestionsItemDtos() {
        AnswerItemDTO answerItemDTO = new AnswerItemDTO();
        AnswerItemDTO answerItemDTO1 = new AnswerItemDTO();
        AnswerItemDTO answerItemDTO2 = new AnswerItemDTO();
        AnswerItemDTO answerItemDTO3 = new AnswerItemDTO();
        answerItemDTO.setId("3");
        answerItemDTO.setAnswerText("Ответ 3");
        answerItemDTO.setIsCorrect(false);
        answerItemDTO1.setId("4");
        answerItemDTO1.setAnswerText("Ответ 2");
        answerItemDTO1.setIsCorrect(true);
        answerItemDTO2.setId("4");
        answerItemDTO2.setAnswerText("Ответ 2");
        answerItemDTO2.setIsCorrect(true);
        answerItemDTO3.setId("4");
        answerItemDTO3.setAnswerText("Ответ 2");
        answerItemDTO3.setIsCorrect(true);

        QuestionsItemDto questionsItemDto = new QuestionsItemDto();
        questionsItemDto.setId("1");
        questionsItemDto.setName("Вопрос 1");
        questionsItemDto.setAnswers(List.of(answerItemDTO, answerItemDTO1));
        QuestionsItemDto questionsItemDto1 = new QuestionsItemDto();
        questionsItemDto1.setId("2");
        questionsItemDto1.setName("Вопрос 2");
        questionsItemDto1.setAnswers(List.of(answerItemDTO2, answerItemDTO3));

        return List.of(questionsItemDto, questionsItemDto1);
    }
}
