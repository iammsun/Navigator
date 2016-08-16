package com.iammsun.navigator;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.iammsun.navigator.annotation.Nav;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by sunmeng on 16/8/15.
 */
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Nav.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> activities = roundEnv.getElementsAnnotatedWith(Nav.class);

        MethodSpec.Builder mapMethod = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .addStatement("com.iammsun.navigator.ExtraTypes extraTypes = new com.iammsun" +
                        ".navigator.ExtraTypes()")
                .addCode("\n");

        for (Element activity : activities) {
            if (activity.getKind() != ElementKind.CLASS) {
                error("Router can only apply on class");
            }
            Nav router = activity.getAnnotation(Nav.class);
            addStatement(mapMethod, "setIntExtra", router.intParams());
            addStatement(mapMethod, "setLongExtra", router.longParams());
            addStatement(mapMethod, "setBooleanExtra", router.booleanParams());
            addStatement(mapMethod, "setShortExtra", router.shortParams());
            addStatement(mapMethod, "setFloatExtra", router.floatParams());
            addStatement(mapMethod, "setDoubleExtra", router.doubleParams());
            addStatement(mapMethod, "setByteExtra", router.byteParams());
            addStatement(mapMethod, "setCharExtra", router.charParams());
            addStatement(mapMethod, "setStringExtra", router.stringParams());
            addStatement(mapMethod, "setDataExtra", router.dataParams());

            for (String formatUri : router.value()) {
                ClassName className = ClassName.get((TypeElement) activity);
                mapMethod.addStatement("com.iammsun.navigator.Navigator.map($S, $T" +
                        ".class, extraTypes)", formatUri, className);
            }
            mapMethod.addCode("\n");
        }

        TypeSpec routerMapping = TypeSpec.classBuilder("RouterMapping")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mapMethod.build())
                .build();
        try {
            JavaFile.builder("com.iammsun.navigator", routerMapping)
                    .build()
                    .writeTo(filer);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    private void addStatement(MethodSpec.Builder mapMethod, String extraMethod, String[] args) {
        String extras = join(args);
        if (extras.length() > 0) {
            mapMethod.addStatement("extraTypes." + extraMethod + "($S.split(\",\"))", extras);
        }
    }

    private String join(String[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        if (args.length == 1) {
            return args[0];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++) {
            sb.append(args[i]).append(",");
        }
        sb.append(args[args.length - 1]);
        return sb.toString();
    }

    private void error(String error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }
}
