<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<%@ taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>
    <div class="container-flued text-center bg-primary text-white m-2 p-1  rounded-4 "> 
        <h1>Report Application</h1>
        <h1>---------------------</h1>
    </div>
    
    <div class="container-flued align-items-center p-3 m-3  mt-0 bg-dark-subtle rounded-1 border ">
        <form:form  action="demo" modelAttribute="search" method="post" class="row gx-3 gy-3">
        
        <div class="col-sm-4">
            <h5>Plan Name :</h5>
                <form:select path="planName" class="form-select">
                    <form:option value="">-Select-</form:option>
                    <form:options items="${names}" />
                </form:select>

        </div>

        <div class="col-sm-4">
            <h5>Plan Status :</h5>
            <form:select  path="planStatus" class="form-select"  >
                <form:option value="" >Choose...</form:option>
                <form:options items="${status}" />
                
            </form:select>
        </div>


        <div class="col-sm-4">
            <h5>Gender :</h5>
            <label class="visually-hidden" for="specificSizeSelect3">Preference</label>
            <form:select class="form-select" id="specificSizeSelect3" path="gender">
                <form:option value="">Choose...</form:option>
                <form:option value="Male">Male</form:option>
                <form:option value="Fe-Male">Fe-Male</form:option>
                
            </form:select>
        </div>

        
        <div class="col-sm-4">

            <h5>Start Date :</h5>
            <form:input path="startDate"  type="date"   class="form-control"/>
        </div>

        <div class="col-sm-4">

            <h5>End Date :</h5> 
            <form:input path="endDate"  type="date"   class="form-control" />
            

        </div>
        
        <div class="col-auto align-self-end pm-1">
            <h5></h5>
            <a href="/" class="btn btn-primary">Reset</a>
        </div>

        <div class="col align-self-end">
            <h5></h5>
            <button type="submit" class="btn btn-primary">Search</button>
        </div>

        

    </div>

  </form:form>

    <div class="container-flued p-3 m-3">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>S.NO.</th>
                    <th>Holder Name</th>
                    <th>Plan Gender</th>
                    <th>Plan Name</th>
                    <th>Plan Status</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Benifit Amount</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${plans}" var="plan"  varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${plan.citizenName}</td>
                        <td>${plan.gender}</td>
                        <td>${plan.planName}</td>
                        <td>${plan.planStatus}</td>
                        <td>${plan.planStartDate}</td>
                        <td>${plan.planEndDate}</td>
                        <td>${plan.benefitAmount}</td>
                    </tr>
                </c:forEach>

                <tr>
                    <c:if test="${empty plans}">
                        <td colspan="8" style="text-align: center ;"> No Records Found... </td>
                    </c:if>
                </tr>    
            </tbody>

        </table>
    </div>

    <div class="container-flued p-3 m-3">
        <h5>Export : <a href="">Excle</a> <a href="">Pdf</a> </h5>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>   
</body>
</html>

