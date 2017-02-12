package com.github.gsdenys.runner.base;

import com.github.gsdenys.CmisInMemoryRunner;
import com.github.gsdenys.runner.Configure;
import com.github.gsdenys.runner.TypeDescriptor;
import com.github.gsdenys.runner.TypeLoader;
import com.github.gsdenys.runner.type.parser.Parser;
import com.github.gsdenys.runner.type.parser.ParserException;
import com.github.gsdenys.runner.type.parser.json.JsonParser;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gsdenys on 12/02/17.
 */
public class DocumentTypeLoader {

    private CmisInMemoryRunner runner;

    /**
     * Constructor
     *
     * @param runner the {@link CmisInMemoryRunner} runner
     */
    public DocumentTypeLoader(CmisInMemoryRunner runner) {
        this.runner = runner;
    }

    public List<TypeDefinition> load() throws ParserException {

        if (this.runner.getTestClass().getJavaClass().isAnnotationPresent(Configure.class)) {
            Configure configure = this.runner.getTestClass().getJavaClass().getDeclaredAnnotation(Configure.class);
            TypeDescriptor[] typeDescArray = configure.docTypes();

            for (TypeDescriptor tDesc : typeDescArray) {
                if(tDesc.loader().equals(TypeLoader.JSON)) {
                    return this.jsonLoader(tDesc.file());
                } else if(tDesc.loader().equals(TypeLoader.ALFRESCO)) {
                    return this.alfrescoLoader(tDesc.file());
                } else if(tDesc.loader().equals(TypeLoader.NUXEO)) {
                    return this.nuxeoLoader(tDesc.file());
                }
            }
        }

        return new ArrayList<TypeDefinition>();
    }

    private List<TypeDefinition> jsonLoader(String  fileName) throws ParserException{
        Parser parser = new JsonParser();
        return parser.getTypes(
                this.runner.getClass().getResourceAsStream(
                        fileName.startsWith("/")? fileName : ("/" + fileName)
                )
        );
    }

    private List<TypeDefinition> alfrescoLoader(String  fileName){
        throw new NotImplementedException();
        //TODO to implement
    }

    private List<TypeDefinition> nuxeoLoader(String  fileName) {
        throw new NotImplementedException();
        //TODO to implement
    }
}
