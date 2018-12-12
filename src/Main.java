import java.lang.annotation.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        UseCollabolator useCollabolator = new UseCollabolator();
        System.out.println(useCollabolator);
        Kontener.inject(useCollabolator,"wstrzykniety kolaborator");
        System.out.println(useCollabolator);
    }
}


// TODO wlasna adnotacja
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Inject{

}

// TODO 2 klasa obiektu do wstrzykiwania

class Collaborator {
    private String name;

    public Collaborator(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "name='" + name + '\'' +
                '}';
    }
}

    //TODO 3 klasa z polami typu collaborator

    class UseCollabolator{
        @Inject
        private Collaborator collaboratorWithAnnotation;

        private Collaborator collaborator;

        @Override
        public String toString() {
            return "UseCollabolator{" +
                    "collaboratorWithAnnotation=" + collaboratorWithAnnotation +
                    ", collaborator=" + collaborator +
                    '}';
        }
    }

    class Kontener{
        private Collaborator collaborator;

        static void inject(Object target, String name) throws IllegalAccessException{
            //TODO 4 pobierz pola obiektu i i przeiteruj po nich

            Field declaredFields [] = target.getClass().getDeclaredFields();
            for(Field field : declaredFields){
                //TODO 5 pobierz adnotacje pola i przeiteruj po nich
                Annotation[] annotations = field.getAnnotations();
                for(Annotation annotation : annotations){
                    //TODO 6 utworz instancje Collaborator i przypisz do pola z adnotacja Inject
                    if("Inject".equals(annotation.annotationType().getName())){
                        field.setAccessible(true); //daje dostep do pola prywatnego
                        field.set(target,new Collaborator(name));
                        //na polu target z adnotacja Inject przypisujemy/wstrzykujemy obiekt
                    }

                }
            }
        }
    }