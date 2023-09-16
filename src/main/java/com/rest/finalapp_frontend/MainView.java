package com.rest.finalapp_frontend;

import com.rest.finalapp_frontend.domain.UserDto;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route
public class MainView extends VerticalLayout {

    /*private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = x`>(Book.class);
    private TextField filter = new TextField();
    private BookForm form = new BookForm(this);
    private Button addNewBook = new Button("Add new book");*/

    private LoginPage loginPage = new LoginPage(this);
    private HomePage homePage = new HomePage(this);
    private UserDto user;

    public MainView() {
        /*filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        grid.setColumns("title", "author", "publicationYear", "type");

        addNewBook.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setBook(new Book());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBook);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);
        form.setBook(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setBook(grid.asSingleSelect().getValue()));*/

        add(loginPage);
        //add(homePage);
    }

    /*public void refresh() {
        grid.setItems(bookService.getBooks());
    }

    public void update() {
        grid.setItems((bookService.findByTitle(filter.getValue())));
    }*/

    public void executeLogin(UserDto user){
        this.user = user;
        remove(loginPage);
        add(homePage);
        if(user.getPlayerId() != null) {
            homePage.getPlayerForm().loadPlayer(user.getPlayerId());
        }
        if(user.getTeamId() != null) {
            homePage.getTeamManageForm().loadTeam(user.getTeamId());
        }
    }

    public UserDto getUser() {
        return user;
    }
}
