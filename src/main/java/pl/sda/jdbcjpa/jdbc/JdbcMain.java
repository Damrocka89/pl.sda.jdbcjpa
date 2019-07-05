package pl.sda.jdbcjpa.jdbc;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

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
//               preparedStatement3();
//           sqlInjectionStatementName("King'; delete from sdajdbc.employee where empno = 7369; -- ");
//          addPayout(new BigDecimal(500));
//        addDepartment(111000, "IT", "Lodz");
//        createDepartmentsInNewLocalization("Moscow");
//        updateDepartment("Sales","Sprzedaż");
//        deleteDepartment(111000);
//        showMeDeptsStartingfromLetter("s");
//            listOfLocationsOfDepts();
//                listOfLocationsUppercase();
//        showManagersWithTheirEmployees();
//        namesOfEmployeesWithSalBetween(BigDecimal.valueOf(2000), BigDecimal.valueOf(3000));
//        showBossWithNoBoss();
//        showNamesWorkingOnDept("Sprzedaż");
//        showSumOfSalariesPerDept();
//        showSurnamesOfPeopleHiredBetween("1992-01-01","1996-01-01");
//        showDeptsWithSalariesCostHigherThan(BigDecimal.valueOf(5000));
//        showManagerWithMaxEmployees();
        showEmployeesWhichSalariesAreLowestThanTheirManagerButHigherThanAnyOfOtherManagers();
//        show5MinSalaries();
//        showEmpWhoEarnsMoreThanManager();


    }

    private static void showEmpWhoEarnsMoreThanManager() {
        try (Connection connection = getConnection()) {
            String query =
                    "select e1.ename, e1.sal from sdajdbc.employee e1, sdajdbc.employee e2 " +
                            "where e1.mgr = e2.empno and " +
                            "e1.sal > e2.sal";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)+": "+resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void show5MinSalaries() {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename, sal from sdajdbc.employee " +
                            "order by sal " +
                            "limit 5";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)+": "+resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showEmployeesWhichSalariesAreLowestThanTheirManagerButHigherThanAnyOfOtherManagers() {
        try (Connection connection = getConnection()) {
            String query =
                    "select distinct W.empno,W.ename,W.sal " +
                            "from (select w.empno,w.ename,w.sal " +
                            "      from sdajdbc.employee w, " +
                            "           sdajdbc.employee m " +
                            "      where w.mgr = m.empno " +
                            "        and w.sal < m.sal) W, " +
                            "     (select * from sdajdbc.employee where empno in (select mgr from sdajdbc.employee)) A " +
                            "where W.sal > A.sal";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2)+": "+resultSet.getBigDecimal(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showManagerWithMaxEmployees() {
        try (Connection connection = getConnection()) {
            String query =
                    "select e1.mgr, e2.ename, count(*) from sdajdbc.employee e1 inner join sdajdbc.employee e2 on e1.mgr=e2.empno " +
                            "group by e1.mgr " +
                            "order by count(*) desc " +
                            "limit 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2)+": "+resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showDeptsWithSalariesCostHigherThan(BigDecimal salary) {
        try (Connection connection = getConnection()) {
            String query =
                    "select dname from sdajdbc.department d inner join sdajdbc.employee e on d.deptno=e.deptno " +
                            "group by dname " +
                            "having sum(sal) > ? " +
                            "order by dname desc";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, salary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showSurnamesOfPeopleHiredBetween(String from, String to) {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename from sdajdbc.employee where hiredate between ? and ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showSumOfSalariesPerDept() {
        try (Connection connection = getConnection()) {
            String query =
                    "select location, job, sum(sal) " +
                            "from sdajdbc.employee e " +
                            "inner join sdajdbc.department d on e.deptno=d.deptno\n" +
                            "group by location, job " +
                            "order by location";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)+": "+resultSet.getString(2)+": "+resultSet.getBigDecimal(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showNamesWorkingOnDept(String deptName) {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename from sdajdbc.employee e inner join sdajdbc.department d on e.deptno=d.deptno  where d.dname=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, deptName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showBossWithNoBoss() {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename from sdajdbc.employee where mgr is null ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void namesOfEmployeesWithSalBetween(BigDecimal min, BigDecimal max) {
        try (Connection connection = getConnection()) {
            String query =
                    "select ename from sdajdbc.employee where sal between ? and ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, min);
            preparedStatement.setBigDecimal(2, max);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showManagersWithTheirEmployees() {
        try (Connection connection = getConnection()) {
            String query =
                    "select mgr, count(*), sum(sal) from sdajdbc.employee\n" +
                            "group by mgr";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Manager id: " + resultSet.getInt(1) + ", has " + resultSet.getInt(2) +
                        " employee(s) with total payouts: " + resultSet.getBigDecimal(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listOfLocationsUppercase() {
        try (Connection connection = getConnection()) {
            String query =
                    "select distinct upper( location) from sdajdbc.department";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listOfLocationsOfDepts() {
        try (Connection connection = getConnection()) {
            String query =
                    "select distinct location from sdajdbc.department";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showMeDeptsStartingfromLetter(String startingLetter) {
        try (Connection connection = getConnection()) {
            String query =
                    "select * from sdajdbc.department where dname like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer deptno = resultSet.getInt(1);
                String dname = resultSet.getString(2);
                String location = resultSet.getString(3);
                System.out.println(deptno + " " + dname + " " + location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDepartment(Integer id) {
        try (Connection connection = getConnection()) {
            String query =
                    "delete from sdajdbc.department where deptno = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateDepartment(String actual, String newName) {
        try (Connection connection = getConnection()) {
            String query =
                    "update sdajdbc.department set dname = ? where dname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, actual);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDepartmentsInNewLocalization(String localization) {
        try (Connection connection = getConnection()) {
            String query =
                    "insert into sdajdbc.department (deptno, dname, location) values (?, ?, ?)";
            String querySelect =
                    "select dname from sdajdbc.department";
            String queryMax =
                    "select max(deptno) from sdajdbc.department";
            PreparedStatement preparedStatementM = connection.prepareStatement(queryMax);
            ResultSet resultSet1 = preparedStatementM.executeQuery();
            Integer max = null;
            while (resultSet1.next()) {
                max = resultSet1.getInt(1);
                max++;
            }
            PreparedStatement preparedStatementSelect = connection.prepareStatement(querySelect);
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                String dname = resultSet.getString(1);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, max);
                preparedStatement.setString(2, dname);
                preparedStatement.setString(3, localization);
                preparedStatement.execute();
                max++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void addDepartment(Integer id, String depName, String localization) {
        try (Connection connection = getConnection()) {
            String query =
                    "insert into sdajdbc.department (deptno, dname, location) values (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, depName);
            preparedStatement.setString(3, localization);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addPayout(BigDecimal bigDecimal) {
        try (Connection connection = getConnection()) {
            String query =
                    "update sdajdbc.employee set total_payouts = coalesce(total_payouts,0) + ?";  //do coalesce mozna kilka wartosci, po kolei sprawdza czy ISNULL
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, bigDecimal);
            preparedStatement.execute();
        } catch (SQLException e) {
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
                    "jdbc:mysql://localhost:3306?useUnicode=true&serverTimezone=UTC", //protokół jdbcjpa
                    "root", "1910");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
