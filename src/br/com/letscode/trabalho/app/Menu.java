package br.com.letscode.trabalho.app;

import java.util.Objects;

public class Menu {
    private Integer idMenu;
    private String descriptionMenu;

    public Menu(Integer id, String description){
        this.idMenu = id;
        this.descriptionMenu = description;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public String getDescriptionMenu() {
        return descriptionMenu;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("idMenu=").append(idMenu);
        sb.append(", descriptionMenu='").append(descriptionMenu).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(idMenu, menu.idMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMenu);
    }
}
