package ifsp.poo.service;

import ifsp.poo.model.Professor;
import ifsp.poo.persistence.ProfessorDAO;

public class AuthenticationService {

    private ProfessorDAO professorDAO;

    public AuthenticationService() {
        professorDAO = new ProfessorDAO();
    }

    public Professor authenticateAndFindProfessor(String username, String password) {
        return professorDAO.getByUsernameAndPassword(username, password);
    }
}
