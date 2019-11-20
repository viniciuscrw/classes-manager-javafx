package ifsp.poo.service;

import ifsp.poo.model.Activity;
import ifsp.poo.model.Student;

import java.util.List;

public interface Corrector {

    void correct(Activity activity, List<Student> students);
}
