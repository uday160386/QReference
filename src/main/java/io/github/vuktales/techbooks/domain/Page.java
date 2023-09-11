//package io.github.vuktales.techbooks.domain;
//
//import jakarta.persistence.*;
//
//import java.util.Objects;
//import java.util.Set;
//
//@Entity
//public class Page {
//
//    public Long getId() {
//        return Id;
//    }
//
//    public void setId(Long id) {
//        Id = id;
//    }
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long Id;
//
//
//    public Set<Author> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(Set<Author> authors) {
//        this.authors = authors;
//    }
//
//    @ManyToMany(mappedBy = "authors")
//    private Set<Author> authors;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Page page = (Page) o;
//        return Objects.equals(Id, page.Id) && Objects.equals(qUrlRef, page.qUrlRef);
//    }
//
//    @Override
//    public String toString() {
//        return "Pages{" +
//                "Id=" + Id +
//                ", qUrlRef='" + qUrlRef + '\'' +
//                '}';
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(Id, qUrlRef);
//    }
//
//    private String qUrlRef;
//
//}
