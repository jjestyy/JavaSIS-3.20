package com.github.jjestyy.JavaSIS320.unit11;

import com.github.jjestyy.JavaSIS320.unit11.dto.*;

import java.util.List;

public class TestData {

    public static JournalRowsRequestDTO getJournalRowsRequestDTO(Boolean isPage, Boolean isSearch, Boolean isFilter) {
        JournalRowsRequestDTO journalRowsRequestDTO = new JournalRowsRequestDTO();
        if(isSearch) {
            journalRowsRequestDTO.setSearch("номер");
        }
        if(isPage) {
            journalRowsRequestDTO.setPageSize(1);
            journalRowsRequestDTO.setPage(0);
        }
        if(isFilter) {
            FilterDto filterDto = new FilterDto();
            filterDto.setCode("question-answer-count");
            filterDto.setType("single-select");
            filterDto.setValue("2");
            journalRowsRequestDTO.setFilters(List.of(filterDto));
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
