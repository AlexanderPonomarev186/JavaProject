import java.util.ArrayList;
import java.util.List;

public class TodoList {
    List<String> listOfToDo;

    public static void main(String[] args) {
        // TODO: написать консольное приложение для работы со списком дел todoList
    }

    public void add(String todo) {
        // TODO: добавьте переданное дело в конец списка
        listOfToDo.add(todo);
    }

    public void add(int index, String todo) {
        // TODO: добавьте дело на указаный индекс,
        //  проверьте возможность добавления
    }

    public void edit(String todo, int index) {
        // TODO: заменить дело на index переданным todo индекс,
        //  проверьте возможность изменения
    }

    public void delete(int index) {
//         TODO: удалить дело находящееся по переданному индексу,
//          проверьте возможность удаления дела
    }

    public ArrayList<String> getTodos() {
        // TODO: вернуть список дел
        return new ArrayList<>();
    }
}