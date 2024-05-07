package com.jee.project.demo.endpoints;

import com.jee.project.demo.Repositories.RoleRepo;
import com.jee.project.demo.Repositories.UserRepo;
import com.jee.project.demo.entities.User;
import lombok.RequiredArgsConstructor;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import templates.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class TeacherEndpoint {

    private final RoleRepo roleRepository;

    private static final String NAMESPACE_URI = "http://google.com";
    private final UserRepo userRepo;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateTeacherRequest")
    @ResponsePayload
    public CreateTeacherResponse
    createTeacher(@RequestPayload CreateTeacherRequest request) {

        // Your logic to create a new teacher
        var teacherRole = roleRepository.findByName("Teacher")
                .orElseThrow(RuntimeException::new);

        var teacher = User.builder()
                .email(request.getEmail())
                .roles(Set.of(teacherRole))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .verified(true)
                .build();
        userRepo.save(teacher);


        ObjectFactory factory = new ObjectFactory();
        CreateTeacherResponse response = factory.createCreateTeacherResponse();
        response.setMessage("Teacher created successfully");

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ReadTeacherRequest")
    @ResponsePayload
    public ReadTeacherResponse readTeacher(@RequestPayload ReadTeacherRequest request) {

        var user = userRepo.findById(Long.valueOf(request.getId()))
                .orElseThrow();

        ObjectFactory factory = new ObjectFactory();
        ReadTeacherResponse response = factory.createReadTeacherResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateTeacherRequest")
    @ResponsePayload
    public UpdateTeacherResponse updateTeacher(@RequestPayload UpdateTeacherRequest request) {


        var teacher = userRepo.findByEmail(request.getEmail()).orElseThrow();
        teacher.setEmail(request.getEmail());
        teacher.setFirstName(request.getLastName());
        teacher.setLastName(request.getLastName());
        userRepo.save(teacher);
        ObjectFactory factory = new ObjectFactory();
        UpdateTeacherResponse response = factory.createUpdateTeacherResponse();
        response.setMessage("Teacher updated successfully");

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteTeacherRequest")
    @ResponsePayload
    public DeleteTeacherResponse deleteTeacher(@RequestPayload DeleteTeacherRequest request) {

        userRepo.deleteById(Long.valueOf(request.getId()));

        ObjectFactory factory = new ObjectFactory();
        DeleteTeacherResponse response = factory.createDeleteTeacherResponse();
        response.setMessage("Teacher deleted successfully");

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllTeachersRequest")
    @ResponsePayload
    public GetAllTeachersResponse getAllTeachers() {

        // Your logic to read a teacher based on ID

        ObjectFactory factory = new ObjectFactory();
        GetAllTeachersResponse response = factory.createGetAllTeachersResponse();
        var teachers = userRepo.findByRoles_Name("Teacher");
        List<TeacherInfo> teacherInfos = teachers.stream()
                .map(teacher -> {
                    TeacherInfo teacherInfo =new TeacherInfo();
                    teacherInfo.setEmail(teacher.getEmail());
                    teacherInfo.setFirstName(teacher.getFirstName());
                    teacherInfo.setLastName(teacher.getLastName());
                    return teacherInfo;
                })
                .collect(Collectors.toList());
        response.setTeachers(teacherInfos);
        return response;
    }
}
