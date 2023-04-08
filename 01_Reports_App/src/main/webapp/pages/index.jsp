<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>

   <div class="container-flued text-center bg-primary text-white"> 
        <h1>Report Application</h1>
        <h1>---------------------</h1>
    </div>

    <div class="container ">

        <form class="row gx-3 gy-2 align-items-center">
            <div class="col-sm-3">
                <h5>Plan Name :</h5>
                <label class="visually-hidden" for="specificSizeSelect">Preference</label>
                <select class="form-select" id="specificSizeSelect">
                    <option selected>Choose...</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
            </div>

            <div class="col-sm-3">
                <h5>Plan Status :</h5>
                <label class="visually-hidden" for="specificSizeSelect">Preference</label>
                <select class="form-select" id="specificSizeSelect">
                    <option selected>Choose...</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </select>
            </div>

            <div class="col-sm-3">
            <h5>Gender :</h5>
            <label class="visually-hidden" for="specificSizeSelect">Preference</label>
            <select class="form-select" id="specificSizeSelect">
                <option selected>Choose...</option>
                <option value="Male">Male</option>
                <option value="Female">Fe-Male</option>
                
            </select>
            </div>
            <div class="col-sm-4">
                <div class="form-check">
                    <label class="form-check-label" for="autoSizingCheck1">
                        Start Date
                    </label>
                    <input class="form-check-input" type="date" id="autoSizingCheck1">
                    
                </div>
            </div>

            <div class="col-sm-4">
                <div class="form-check">
                    <label class="form-check-label" for="autoSizingCheck2">
                        End Date
                    </label>
                    <input class="form-check-input" type="date" id="autoSizingCheck2">
                    
                </div>
            </div>

            <div class="col-auto">
            <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>   
</body>
</html>

