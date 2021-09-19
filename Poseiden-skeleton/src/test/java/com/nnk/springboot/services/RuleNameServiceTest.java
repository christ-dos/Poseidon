package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    private RuleNameService ruleNameServiceTest;

    @Mock
    private RuleNameRepository ruleNameRepositoryMock;

    private RuleName ruleNameTest;

    @BeforeEach
    public void setPerTest() {
        ruleNameServiceTest = new RuleNameService(ruleNameRepositoryMock);

        ruleNameTest = RuleName.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("Sql str")
                .sqlPart("Sql part")
                .build();
    }

    @Test
    public void getRulesNamesTest_whenListOfRuleNameContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<RuleName> ruleNames = new ArrayList<>(
                Arrays.asList(
                        ruleNameTest = RuleName.builder().id(1).name("Name").description("Description")
                                .json("Json").template("Template").sqlStr("Sql str").sqlPart("sql part").build(),
                        ruleNameTest = RuleName.builder().id(2).name("Name1").description("Description1")
                                .json("Json1").template("Template1").sqlStr("Sql str1").sqlPart("sql part1").build(),
                        ruleNameTest = RuleName.builder().id(3).name("Name2").description("Description2")
                                .json("Json2").template("Template2").sqlStr("Sql str2").sqlPart("sql part2").build()));
        when(ruleNameRepositoryMock.findAll()).thenReturn(ruleNames);
        //WHEN
        List<RuleName> ruleNamesResult = ruleNameServiceTest.getRulesNames();
        //THEN
        verify(ruleNameRepositoryMock, times(1)).findAll();
        assertEquals(ruleNames, ruleNamesResult);
        assertTrue(ruleNamesResult.size() > 0);
    }

    @Test
    public void getRulesNameByIdTest_whenRulesNameFound_thenReturnRulesName() {
        //GIVEN
        when(ruleNameRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.of(ruleNameTest));
        //WHEN
        RuleName ruleNameByIdResult = ruleNameServiceTest.getRuleNameById(ruleNameTest.getId());
        //THEN
        verify(ruleNameRepositoryMock, times(1)).findById(isA(Integer.class));
        assertNotNull(ruleNameByIdResult);
        assertEquals("Name", ruleNameByIdResult.getName());
        assertEquals("Description", ruleNameByIdResult.getDescription());
        assertEquals("Json", ruleNameByIdResult.getJson());
        assertEquals("Template", ruleNameByIdResult.getTemplate());
        assertEquals("Sql str", ruleNameByIdResult.getSqlStr());
        assertEquals("Sql part", ruleNameByIdResult.getSqlPart());
    }

    @Test
    public void getRuleNameByIdTest_whenRuleNameNotFound_thenRuleNameNotFoundException() {
        //GIVEN
        when(ruleNameRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(ruleNameRepositoryMock, times(0)).findById(isA(Integer.class));
        assertThrows(RuleNameNotFoundException.class, () -> ruleNameServiceTest.getRuleNameById(ruleNameTest.getId()));
    }

    @Test
    public void addRuleNameTest_whenRuleNameNotRecordedInDb_thenReturnRuleNameAdded() {
        //GIVEN
        when(ruleNameRepositoryMock.save(isA(RuleName.class))).thenReturn(ruleNameTest);
        //WHEN
        RuleName ruleNameResult = ruleNameServiceTest.addRuleName(ruleNameTest);
        //THEN
        verify(ruleNameRepositoryMock, times(1)).save(isA(RuleName.class));
        assertEquals("Name", ruleNameResult.getName());
        assertEquals("Description", ruleNameResult.getDescription());
        assertEquals("Json", ruleNameResult.getJson());
        assertEquals("Template", ruleNameResult.getTemplate());
        assertEquals("Sql str", ruleNameResult.getSqlStr());
        assertEquals("Sql part", ruleNameResult.getSqlPart());
    }

    @Test
    public void updateRatingTest_whenRatingExist_thenReturnRatingUpdated() {
        //GIVEN
        RuleName ruleNameTestUpdated = RuleName.builder()
                .id(1)
                .name("Name updated")
                .description("Description updated")
                .json("Json updated")
                .template("Template updated")
                .sqlStr("Sql str updated")
                .sqlPart("Sql part updated")
                .build();

        when(ruleNameRepositoryMock.getById(isA(Integer.class))).thenReturn(ruleNameTest);
        when(ruleNameRepositoryMock.save(isA(RuleName.class))).thenReturn(ruleNameTestUpdated);
        //WHEN
        RuleName ruleNameUpdatedResult = ruleNameServiceTest.updateRuleName(ruleNameTestUpdated);
        //THEN
        verify(ruleNameRepositoryMock, times(1)).save(isA(RuleName.class));
        assertEquals("Name updated", ruleNameUpdatedResult.getName());
        assertEquals("Description updated", ruleNameUpdatedResult.getDescription());
        assertEquals("Json updated", ruleNameUpdatedResult.getJson());
        assertEquals("Template updated", ruleNameUpdatedResult.getTemplate());
        assertEquals("Sql str updated", ruleNameUpdatedResult.getSqlStr());
        assertEquals("Sql part updated", ruleNameUpdatedResult.getSqlPart());
    }
    @Test
    public void updateRuleNameTest_whenRuleNameNotExist_thenThrowRuleNameNotFoundException() {
        //GIVEN
        when(ruleNameRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(ruleNameRepositoryMock, times(0)).save(isA(RuleName.class));
        assertThrows(RuleNameNotFoundException.class, () -> ruleNameServiceTest.updateRuleName(ruleNameTest));
    }

    @Test
    public void deleteRuleNameTest_whenRuleNameExist_ThenReturnMessageRuleNameDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = ruleNameServiceTest.deleteRuleName(id);
        //THEN
        verify(ruleNameRepositoryMock,times(1)).deleteById(anyInt());
        assertEquals("RuleName deleted", messageResult);
    }

}
