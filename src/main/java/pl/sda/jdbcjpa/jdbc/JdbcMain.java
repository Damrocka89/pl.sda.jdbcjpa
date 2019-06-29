package pl.sda.jdbcjpa.jdbc;

import java.math.BigDecimal;
import java.sql.*;

public class JdbcMain {

    public static void main(String[] args) {

//        statement();
//        System.out.println("************************");
//        preparedStatement();
//        System.out.println("************************");
//        callableStatement();
//        System.out.println("************************");
//        preparedStatement2();
//        System.out.println("******************");
        //       preparedStatement3();
     //   sqlInjectionStatementName("King'; delete from sdajdbc.employee where empno = 7369; -- ");
        addPayout(new BigDecimal(500));

    }

    private static void addPayout(BigDecimal bigDecimal) {
        try (Connection connection = getConnection()) {
            String query =
                    "update sdajdbc.employee set total_payouts = coalesce(total_payouts,0) + ?";  //do coalesce mozna kilka wartosci, po kolei sprawdza czy ISNULL
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, bigDecimal);
            preparedStatement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void preparedStatement3() {
        int minSalary = 100;
        int maxSalary = 1000;

        try (Connection connection = getConnection()) {
            String query =
                    "select ename, job, sal, mgr " +
                            "from sdajdbc.employee " +
                            "where sal > ? and sal < ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minSalary);
            preparedStatement.setInt(2, maxSalary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                Integer sal = resultSet.getInt("sal");
                Integer mgr = resultSet.getInt("mgr");
                if (resultSet.wasNull()) {
                    mgr = null;
                }
                System.out.println(ename + " " + job + " " + sal + " " + mgr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void preparedStatement2() {
        int minSalary = 100;
        try (Connection connection = getConnection()) {
            String query =
                    "select ename, job, sal, mgr " +
                            "from sdajdbc.employee " +
                            "where sal > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minSalary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                Integer mgr = resultSet.getInt("mgr");
                if (resultSet.wasNull()) {
                    mgr = null;
                }
                System.out.println(ename + " " + job + " " + sal + " " + mgr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void callableStatement() {
        try (Connection connection = getConnection();) {
            String query = "{call sdajdbc.getName(?,?)}";
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, 7654);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.execute();
            String string = callableStatement.getString(2);
            System.out.println(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void preparedStatement() {
        int minSalary = 100;
        try (Connection connection = getConnection()) {
            String query =
                    "select ename, job, sal " +
                            "from sdajdbc.employee " +
                            "where sal > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minSalary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void statement() {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename, job, sal " +
                            "from sdajdbc.employee " +
                            "where sal > 100";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void sqlInjectionStatementName(String name) {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename, job, sal " +
                            "from sdajdbc.employee " +
                            "where ename ='" + name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbcjpa:mysql://localhost:3306?useUnicode=true&serverTimezone=UTC", //protokół jdbcjpa
                    "root", "1910");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
