package com.example.zdd_viewinjector_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AnnotatedClass {
    public TypeElement mClassElement;
    public List<BindViewField> mFields;
    public Elements mElementUtils;

    public AnnotatedClass(TypeElement element,Elements elements){
        this.mClassElement = element;
        this.mFields = new ArrayList<>();
        this.mElementUtils = elements;
    }

    public void addField(BindViewField field){
        this.mFields.add(field);
    }

    public String getFullClassName(){
        return mClassElement.getQualifiedName().toString();
    }

    public JavaFile generateInjector(){
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(CompilerUtil.PROVIDER, "provider");

        for (BindViewField field : mFields) {
            // find views
            injectMethodBuilder.addStatement("host.$N = ($T)(provider.findView(source, $L))", field.getFieldName(),
                    ClassName.get(field.getFieldType()), field.getResId());
        }
        TypeSpec injectClass = TypeSpec.classBuilder(mClassElement.getSimpleName() + "$$Finder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(CompilerUtil.FINDER,TypeName.get(mClassElement.asType())))
                .addMethod(injectMethodBuilder.build())
                .build();
        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        return JavaFile.builder(packageName,injectClass).build();
    }
}
