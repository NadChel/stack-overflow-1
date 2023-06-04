package stack.overflow.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import stack.overflow.IntegrationTestContext;
import stack.overflow.controller.expectationTesters.ExpectationTester;
import stack.overflow.controller.expectationTesters.GetCommentExpectationTester;
import stack.overflow.controller.expectationTesters.GetCommentPageExpectationTester;
import stack.overflow.model.dto.request.QuestionCommentRequestDto;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserQuestionCommentRestControllerTest extends IntegrationTestContext {
    private static final String testUsername = "mickey_m";
    private static final String testPassword = "mickey";
    private String token;
    private static final String BASE_URI = "/api/v1/user/question-comments/";
    private static final String BASE_SCRIPT_PATH = "/sql/UserQuestionCommentRestControllerTest/";
    private ExpectationTester expectationTester;

    public UserQuestionCommentRestControllerTest() {

    }

    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "CreateCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "CreateCommentTest/after.sql")
    @Test
    public void createCommentTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        QuestionCommentRequestDto dto =
                new QuestionCommentRequestDto("text", 1L);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(qc.id) = 1
                                FROM QuestionComment qc
                                JOIN qc.owner ow
                                JOIN qc.question q
                                WHERE qc.text = 'text'
                                AND q.createdDate IS NOT NULL
                                AND q.modifiedDate IS NOT NULL
                                AND ow.id = 1
                                """, Boolean.class)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "DeleteCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "DeleteCommentTest/after.sql")
    public void deleteCommentTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        mockMvc.perform(delete(BASE_URI + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        assertTrue(entityManager.createQuery("""
                        SELECT COUNT(qc.id) = 0
                        FROM QuestionComment qc
                        """, Boolean.class)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetCommentTest/after.sql")
    public void getCommentTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentExpectationTester.Builder(response)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneDefaultTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(10)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByIdTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByIdDescTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "ID_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(5)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByIdTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(6)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByIdDescTest() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "ID_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(10)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByCreatedDate() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "CREATED_DATE_ASC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(2)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByCreatedDateDesc() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "CREATED_DATE_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(8)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByCreatedDate() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "CREATED_DATE_ASC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(9)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByCreatedDateDesc() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "CREATED_DATE_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(10)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByModifiedDate() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "MODIFIED_DATE_ASC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(3)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveSortByModifiedDateDesc() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "MODIFIED_DATE_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(9)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByModifiedDate() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "MODIFIED_DATE_ASC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(10)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveSortByModifiedDateDesc() throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5")
                        .param("sortType", "MODIFIED_DATE_DESC"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(8)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
}
