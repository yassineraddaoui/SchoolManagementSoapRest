
package templates;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the templates package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: templates
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllTeachersResponse }
     * 
     * @return
     *     the new instance of {@link GetAllTeachersResponse }
     */
    public GetAllTeachersResponse createGetAllTeachersResponse() {
        return new GetAllTeachersResponse();
    }

    /**
     * Create an instance of {@link TeacherInfo }
     * 
     * @return
     *     the new instance of {@link TeacherInfo }
     */
    public TeacherInfo createTeacherInfo() {
        return new TeacherInfo();
    }

    /**
     * Create an instance of {@link CreateTeacherRequest }
     * 
     * @return
     *     the new instance of {@link CreateTeacherRequest }
     */
    public CreateTeacherRequest createCreateTeacherRequest() {
        return new CreateTeacherRequest();
    }

    /**
     * Create an instance of {@link CreateTeacherResponse }
     * 
     * @return
     *     the new instance of {@link CreateTeacherResponse }
     */
    public CreateTeacherResponse createCreateTeacherResponse() {
        return new CreateTeacherResponse();
    }

    /**
     * Create an instance of {@link ReadTeacherRequest }
     * 
     * @return
     *     the new instance of {@link ReadTeacherRequest }
     */
    public ReadTeacherRequest createReadTeacherRequest() {
        return new ReadTeacherRequest();
    }

    /**
     * Create an instance of {@link ReadTeacherResponse }
     * 
     * @return
     *     the new instance of {@link ReadTeacherResponse }
     */
    public ReadTeacherResponse createReadTeacherResponse() {
        return new ReadTeacherResponse();
    }

    /**
     * Create an instance of {@link UpdateTeacherRequest }
     * 
     * @return
     *     the new instance of {@link UpdateTeacherRequest }
     */
    public UpdateTeacherRequest createUpdateTeacherRequest() {
        return new UpdateTeacherRequest();
    }

    /**
     * Create an instance of {@link UpdateTeacherResponse }
     * 
     * @return
     *     the new instance of {@link UpdateTeacherResponse }
     */
    public UpdateTeacherResponse createUpdateTeacherResponse() {
        return new UpdateTeacherResponse();
    }

    /**
     * Create an instance of {@link DeleteTeacherRequest }
     * 
     * @return
     *     the new instance of {@link DeleteTeacherRequest }
     */
    public DeleteTeacherRequest createDeleteTeacherRequest() {
        return new DeleteTeacherRequest();
    }

    /**
     * Create an instance of {@link DeleteTeacherResponse }
     * 
     * @return
     *     the new instance of {@link DeleteTeacherResponse }
     */
    public DeleteTeacherResponse createDeleteTeacherResponse() {
        return new DeleteTeacherResponse();
    }

}
