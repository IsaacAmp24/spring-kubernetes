## Microservicio de Cursos

### Descripción

#### Anotaciones

``` java

// en la clase Course.java

@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // 
private List<CourseUsers> courseUsers; // un curso puede tener varios usuarios

se usa el atributo orphanRemoval = true para que cuando se elimine un curso,
                                   se eliminen los usuarios asociados a ese curso

```

``` java
```