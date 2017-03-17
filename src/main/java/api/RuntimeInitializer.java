package api;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class RuntimeInitializer {
    public static <T> void initFIeld(final Class<T> type) {
        Stream.of(FieldUtils.getAllFields(type))
                .filter(isRuntimeLocator)
                .filter(notInitialized)
                .forEach(initializeWithActiveXpath);
    }

    private static Predicate<Field> isRuntimeLocator = field -> RuntimeXpath.class.equals(field.getType());

    private static Predicate<Field> notInitialized = field -> {
        boolean shouldInit = true;
        try {
            shouldInit = Objects.isNull(FieldUtils.readStaticField(field, true));
        } catch (final Exception e) {
        }
        return shouldInit;
    };

    private static Consumer<Field> initializeWithActiveXpath = field -> {
        field.setAccessible(true);
        final String activeXpath = getLocatorFromAnnotation(field);
        try {
            FieldUtils.writeStaticField(field, new RuntimeXpath(activeXpath), true);
        } catch (final Exception e) {
            throw new IllegalStateException("Fail to initialize locator field at runtime.");
        }
    };

    private static String getLocatorFromAnnotation(final Field field) {
        final Class<Usage> annotationType = Usage.class;
        final Usage[] annotations = ofNullable(field.getAnnotationsByType(annotationType))
                .orElseThrow(() -> new IllegalArgumentException(format("Field [%s] does not have @%s annotations!",
                        field.toString(), annotationType.getSimpleName())));

        final Predicate<Usage> isActiveAnnotation = a -> SpecificApplication.getActiveSite().equals(a.app());
        final String xpath = Stream.of(annotations)
                .filter(isActiveAnnotation)
                .map(Usage::xpath)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("Field [%s] does not have @%s annotation for %s site version!", field.toString(),
                                annotationType.getSimpleName(), SpecificApplication.getActiveSiteName())));
        return xpath;
    }
}
