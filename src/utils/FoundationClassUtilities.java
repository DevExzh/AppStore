package utils;

import java.util.Collection;

/**
 * Additional classes and methods to support more features like C/C++ like style coding
 * @author Ryker Zhu
 */
public class FoundationClassUtilities {
    @FunctionalInterface
    public interface FunctionPointer<R, P> {
        R invoke(P parameter);
    }

    @FunctionalInterface
    public interface VoidFunctionPointer {
        void invoke();
    }

    public static int scanValidInteger(String prompt, FunctionPointer<Boolean, Integer> callback) {
        int returnValue;
        for(;;) {
            if(callback.invoke(returnValue = ScannerInput.validNextInt(prompt))) {
                return returnValue;
            }
            System.out.println("Sorry, that is an invalid value, please enter again!");
        }
    }

    /**
     * A helper class that helps to count, sum, calculate, etc.
     * @param <E> the type of the elements that the collection contains
     * @author Ryker Zhu
     */
    public static class Statistics<E> {
        private Collection<E> collection;

        public Statistics(Collection<E> collection) {
            this.collection = collection;
        }

        public int total() {
            return collection.size();
        }

        /**
         * Calculate the number of elements in the collection with the condition satisfied
         * @param callback the function pointer that returns the boolean indicating whether the element given meets the condition
         * @return the number of elements in the collection with the condition satisfied
         */
        public int total(FunctionPointer<Boolean, E> callback) {
            int returnValue = 0;
            for(E o : collection) {
                if(callback.invoke(o)) ++returnValue;
            }
            return returnValue;
        }

        public double average(FunctionPointer<Double, E> callback) {
            double sum = 0;
            for(E o : collection) {
                sum += callback.invoke(o);
            }
            return sum / total();
        }

        public double average(FunctionPointer<Double, E> callback, FunctionPointer<Boolean, E> condition) {
            double sum = 0, num = 0;
            for(E o : collection) {
                if(condition.invoke(o)) {
                    sum += callback.invoke(o);
                    ++num;
                }
            }
            return sum / num;
        }
    }
}
