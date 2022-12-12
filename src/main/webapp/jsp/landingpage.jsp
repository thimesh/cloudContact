<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cloud Contact</title>
<style>
.container{
	display: inline-block;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width: 200px;
    height: 100px;
    margin: auto;
}
.line {
      border-left: 6px solid;
      height: 200px;
      position:absolute;
      left: 50%;
    }
	
</style>
<%@include file="cdn.jsp"%></head>

<body>
    	<h1><center>Student Info</center></h1>


    <div class="container">
	  <div class="row" style="padding-right: 107px;">
	    <div class="col-sm">
	    	<button type="button" class="btn btn btn-success"><a href="/login">Login</a></button>
	    </div>
	    <div class="col-sm">
	      <hr class="line">
	    </div>
	    <div class="col-sm">
	    	<button type="button" class="btn btn btn-success"><a href="/postregister">Register</a></button>
	    </div>
	  </div>
</div>

</body>
</html>
