package examples.servlets;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ExampleServletTest
{
    /**
     * Test for method: tryThis(javax.servlet.ServletContext)
     * 
     * @throws Throwable Tests may throw any Throwable
     * @see ExampleServlet#tryThis(javax.servlet.ServletContext)
     * @author Parasoft Jtest 10.0
     */
    @Test
    public void testTryThis() throws Throwable
    {
        ExampleServlet THIS = new ExampleServlet();
        try {
            THIS.tryThis((ServletContext) null);
        } catch (NullPointerException npe) {
            // expected
            return;
        }
        fail();
    }

    /**
     * Test for method: tryThis(javax.servlet.ServletContext)
     * 
     * @throws Throwable Tests may throw any Throwable
     * @see ExampleServlet#tryThis(javax.servlet.ServletContext)
     * @author Parasoft Jtest 10.0
     */
    @Test
    public void testTryThis2() throws Throwable
    {

        ExampleServlet THIS = new ExampleServlet();
        ServletContext context = mock(ServletContext.class);
        when(context.getInitParameter(anyString())).thenReturn(null);
        try {
            THIS.tryThis(context);
        } catch (NullPointerException npe) {
            // expected
            return;
        }
        fail();
    }

    /**
     * Test for method: tryThis(javax.servlet.ServletContext)
     * 
     * @throws Throwable Tests may throw any Throwable
     * @see ExampleServlet#tryThis(javax.servlet.ServletContext)
     * @author Parasoft Jtest 10.0
     */
    @Test
    public void testTryThis3() throws Throwable
    {
        ExampleServlet THIS = new ExampleServlet();

        ServletContext context = mock(ServletContext.class);
        when(context.getInitParameter(anyString())).thenReturn("");
        Enumeration enumeration = mock(Enumeration.class);
        when(enumeration.hasMoreElements()).thenReturn(false);
        when(context.getInitParameterNames()).thenReturn(enumeration);

        THIS.tryThis(context);
        assertEquals(null, THIS.getServletConfig());
        assertEquals("", THIS.getServletInfo());
    }

    /**
     * Test for method: tryThis(javax.servlet.ServletContext)
     * 
     * @throws Throwable Tests may throw any Throwable
     * @see ExampleServlet#tryThis(javax.servlet.ServletContext)
     * @author Parasoft Jtest 10.0
     */
    @Test
    public void testTryThis4() throws Throwable
    {
        ExampleServlet THIS = new ExampleServlet();
        ServletContext var1 = mock(ServletContext.class);
        when(var1.getInitParameter(anyString())).thenReturn("0");
        Enumeration enumeration = mock(Enumeration.class);
        when(enumeration.hasMoreElements()).thenReturn(true, true, false);
        when(enumeration.nextElement()).thenReturn("hello world", 5);

        when(var1.getInitParameterNames()).thenReturn(enumeration);

        THIS.tryThis(var1);
        assertEquals(null, THIS.getServletConfig());
        assertEquals("", THIS.getServletInfo());
    }

    /**
     * Parasoft Jtest UTA: Test for doGet(HttpServletRequest, HttpServletResponse)
     *
     * @see examples.servlets.ExampleServlet#doGet(HttpServletRequest, HttpServletResponse)
     * @author shwang
     */
    @Test(timeout = 5000)
    public void testDoGet() throws Throwable
    {
        // Given
        ExampleServlet underTest = new ExampleServlet();

        // When
        HttpServletRequest request = mock(HttpServletRequest.class);
        String getParameterResult = "John"; // UTA: 配置的值
        when(request.getParameter(nullable(String.class))).thenReturn(getParameterResult);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter getWriterResult = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(getWriterResult);
        underTest.doGet(request, response);

    }

    /**
     * Parasoft Jtest UTA: Test for doGet(HttpServletRequest, HttpServletResponse)
     *
     * @see examples.servlets.ExampleServlet#doGet(HttpServletRequest, HttpServletResponse)
     * @author shwang
     */
    @Test(timeout = 5000)
    public void testDoGet2() throws Throwable
    {
        // Given
        ExampleServlet underTest = new ExampleServlet();

        // When
        HttpServletRequest request = mock(HttpServletRequest.class);
        String getParameterResult = ""; // UTA: 配置的值
        when(request.getParameter(nullable(String.class))).thenReturn(getParameterResult);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter getWriterResult = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(getWriterResult);
        underTest.doGet(request, response);

    }

}
