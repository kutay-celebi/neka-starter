package tr.com.nekasoft.core.jpa.bean;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 12.10.2019
 */
public enum NekaEntityPathResolver implements EntityPathResolver {

    INSTANCE;

    @Override
    public <T> EntityPath<T> createPath(Class<T> domainClass) {
        String queryClassName = this.getQueryClassName(domainClass);

        try {
            Class<?> pathClass = ClassUtils.forName(queryClassName, domainClass.getClassLoader());
            return (EntityPath<T>) this.getStaticFieldOfType(pathClass).map(
                    field -> ReflectionUtils.getField(field, null)).orElseThrow(
                    () -> new IllegalStateException(
                            String.format("Did not find a static field of the same type in %s!",
                                          pathClass)));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    String.format("Did not find a query class %s for domain class %s!",
                                  queryClassName, domainClass.getName()));
        }
    }

    private Optional<Field> getStaticFieldOfType(Class<?> type) {
        for (Field declaredField : type.getDeclaredFields()) {
            boolean isStatic = Modifier.isStatic(declaredField.getModifiers());
            boolean hasSameType = type.equals(declaredField.getType());
            if (isStatic && hasSameType) {
                return Optional.of(declaredField);
            }
        }
        Class<?> superclass = type.getSuperclass();
        return Object.class.equals(superclass) ? Optional.empty() : this.getStaticFieldOfType(
                superclass);
    }

    private String getQueryClassName(Class<?> domainClass) {
        String simpleClassName = ClassUtils.getShortName(domainClass);
        return String.format("%s.query.%s%sQuery", domainClass.getPackage().getName(),
                             this.getClassBase(simpleClassName), domainClass.getSimpleName());
    }

    private String getClassBase(String classShortName) {
        String[] packages = classShortName.split("\\.");
        return packages.length < 2 ? "" : packages[0] + "_";
    }
}
