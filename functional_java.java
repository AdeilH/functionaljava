
import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Map;

// Immutability -> Data is immutable;
// Functional Purity -> Functions don't change the state always return the same output given the same input 
// First-CLass Functions -> can be treated as objects

public class function_interface{
    static class Person {
        public final String name;
        public final Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

    static class Car {
        public final String make;
        public final String color;
        public final Float price;

        public Car(String make, String color, Float price) {
            this.make = make;
            this.color = color;
            this.price = price;
        }
    }

    static class Employee {
        public final String name;
        public final Integer age;
        public final String jobTitle;
        public final Float salary;

        public Employee(String name, Integer age, String jobTitle, Float salary) {
            this.name = name;
            this.age = age;
            this.jobTitle = jobTitle;
            this.salary = salary;
        }
    }
    public interface triFunc<R, A, B, C>{
        R apply(A a, B b, C c);
    }
    protected static class MyMath{
        public static Integer triple(Integer x){
            return x *3;
        }
    }
    public static void main(String[] args){
        Function <Integer, Integer> MyTriple = MyMath::triple;
        Integer result = MyTriple.apply(5);
        System.out.println(result);

        Function <Integer, Integer> LambdaTriple = (someArg) ->  someArg*3 ;

        triFunc <Double, Double, Double, Double> doubler = (doubleresque,doubleresque2, doubleresque3) -> doubleresque * 3;
        Function <String, Integer> TestString = (SomeString) -> SomeString.length(); 
        System.out.println(doubler.apply(3.0,3.0,3.0));
        Integer[] intARray = { 1,2,5,3,4};
        List<Integer> listofInteger = new ArrayList<>(Arrays.asList(intARray));
        List<Integer> doubledlist = listofInteger.stream().map(LambdaTriple).collect(Collectors.toList());
        System.out.println(doubledlist);
        Function<Integer, Predicate<Integer>> greaterThanFunctor = (Integer x) -> {
            return (y) -> y > x;  
        };
        Predicate<Integer> greaterThanFour = greaterThanFunctor.apply(4);
        
        // Predicate<Integer>  greaterThan2 = (Integer x) -> {return x>2;};
        List<Integer> filteredList = doubledlist.stream().filter(greaterThanFour).collect(Collectors.toList());
        System.out.println(filteredList);

        BinaryOperator<Integer> getSum = (acc, x) -> {
            Integer ResultSum = acc + x; 
            return ResultSum;
        }; 
        System.out.println(filteredList.stream().reduce(0, getSum));

        // Java Standard Collectors
        // ToList
        // ToSet
        // joining()
        // collecting()
        //groupingBy() 
        // Partitioning By()

        Person[] peopleArr = {
                new Person("Brandon", 23),
                new Person("Hank", 43),
                new Person("Jenna", 33),
                new Person("Veronica", 56),
                new Person("Jack", 27),
        };
        List<Person> people = new ArrayList<>(Arrays.asList(peopleArr));

        Function<Person, String> getName = (x) -> x.name;
        // Answer 1 goes here
        List<String> peoplesNames = people.stream().map(getName).collect(Collectors.toList());
        System.out.println(peoplesNames);
        Car[] carsArr = {
                new Car("Chevy", "red", 45000f),
                new Car("Ford", "blue", 23000f),
                new Car("Toyota", "grey", 14000f),
                new Car("Lamborghini", "blue", 150000f),
                new Car("Renault", "blue", 150000f),
        };
        // Answer 2 goes here
        List<Car> cars = new ArrayList<>(Arrays.asList(carsArr));
        Function<String, Predicate<Car>> getcarofColor = (x) -> {
            return (Car y) -> {return y.color == x;}; 
        };
        Predicate<Car> getBlueCar = getcarofColor.apply("blue");
        List<Car> filtered_cars = cars.stream().filter(getBlueCar).collect(Collectors.toList());
        System.out.println(filtered_cars.stream().map((Car x) -> x.make).collect(Collectors.toList()));
        Employee[] employeesArr = {
                new Employee("John", 34, "developer", 80000f),
                new Employee("Hannah", 24, "developer", 95000f),
                new Employee("Bart", 50, "sales executive", 100000f),
                new Employee("Sophie", 49, "construction worker", 40000f),
                new Employee("Darren", 38, "writer", 50000f),
                new Employee("Nancy", 29, "developer", 75000f),
        };
          // Answer 3 goes here
        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));
        List<Float> salariesList = employees.stream().map(x -> x.salary).collect(Collectors.toList());
        BinaryOperator<Float> totalSalaries = (x, y) -> {
            Float total = x+y;
            return total; 
        };
        Float totalSalary = salariesList.stream().reduce(0f, totalSalaries);
        System.out.println(totalSalary);

        Map<String, List<Employee>> groupedByJobTitle = employees
        .stream()
        .collect(Collectors.groupingBy(
                (employee) -> employee.jobTitle
        )); 
        System.out.println(groupedByJobTitle);
        Map<String, Float> avgSalaries = groupedByJobTitle.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        (entry) -> entry.getKey(),
                        (entry) -> entry.getValue()
                                        .stream()
                                        .map((employee) -> employee.salary)
                                        .reduce(0f, totalSalaries)/entry.getValue().size()
                ));
                System.out.println(avgSalaries);
        // Partial application fix some number of arguments configure general functions into specific functions

        triFunc<Integer, Integer, Integer, Integer> add = (x,y,z ) -> x+y+z;
        // Partial Specialization
        Function<Integer, BiFunction<Integer, Integer, Integer>> addpartial = (x) -> (y,z) -> add.apply(x, y, z);
        BiFunction<Integer, Integer, Integer> add5 = addpartial.apply(5);
        System.out.println(add5.apply(10, 32));

        // Currying 

        Function<Integer, Function<Integer, Function<Integer, Integer>>> addcurrying = (x) -> (y) -> (z) -> add.apply(x, y, z);

        Function<Integer, Function<Integer, Integer>> addcurrying5 = addcurrying.apply(5);

        Function<Integer, Integer> addcurrying5and6 = addcurrying5.apply(6);

      //  Integer sum = addcurrying5and6.apply(7);

        System.out.println(addcurrying.apply(5).apply(6).apply(7));

        // Composition compose two functions with .compose happens in reverse order than you expect
        // And Then does the same thing but the same order 

        Function<Integer, Integer> timesTwo = x -> x * 2;
        Function<Integer, Integer> minusOne = x -> x - 1;

        Function<Integer, Integer> timesTwoMinusOne = minusOne.andThen(timesTwo);

        System.out.println(timesTwoMinusOne.apply(10));
    }
}