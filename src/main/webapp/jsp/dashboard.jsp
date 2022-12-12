<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
String iv = "3ad5485e60a4fecde36fa49ff63817dc";
%>
<head>
<meta charset="UTF-8">
<title>${pageName}</title>
<%@include file="cdn.jsp"%>
<style type="text/css">
.vl {
	border-left: 2px solid #b7b7b7;
	height: 300px;
	margin-left: 98px;
	margin-top: 95px;
	}
	.w3-sidebar {
    	overflow: hidden !important;
}

</style>
<style type="text/css" href="/css/card.css"></style>
</head>
<%@include file="cdn.jsp"%></head>

<body>
	<header>
		<div class="top-bar_sub_w3layouts container-fluid">
			<div class="row">
				<div class="col-md-12 logo text-left" style="z-index: 99999;">

					<a class="navbar-brand" href="">
						<h2>Hi ${user.name}</h2>

					</a>

				</div>

			</div>
		</div>
	</header>
	<div>
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-4">
					<%@include file="sidebar.jsp" %>
				</div>
				<div class="col-md-8">
					<h1>Dashboard</h1>
					<br />
					<h3>Search Contacts</h3>

					<div class="col-md-4">
						<input type="text" id="myInput" placeholder="Search names.." title="Type in a name">
						<hr>
						<button type="button" class="btn btn btn-success" onClick="srhCon()">Search</button>

					</div>
					<hr>
					<div id='container1'>

						</div>

				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function srhCon(){
			var data = $("#myInput").val();
			if(data == ''){
				return;
			}
			fetch("/search/"+data)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				if(data.length == 0){

					let text= "<div class='row'>"
						text+="<div class='col-sm-6'>"
						text+="<div class='card'>"
						text+="<div class='card-body'>"
						text+="<h5 class='card-title'>Result</h5>"
						text+="<a href='#' class='btn btn-primary'>contact not found</a> </div></div></div></div>"


					$('#container1').html(text);
					$('#container1').show();
				}else{
					let text= "<div class='row'>"
						text+="<div class='col-sm-6'>"


					data.forEach((contact) => {

						    text+="<div class='card'>"
							text+="<div class='card-body'>"
							text+="<h5 class='card-title'><a href='/user/show/"+contact.cId+"'>"+contact.name+"</a></h5>"
							text+="<a href='#' class='btn btn-primary'>Contact present</a> </div></div>"
							text+="<hr>"
					})

					text+="</div>";
					text+="</div>";
					$('#container1').html(text);
					$('#container1').show();
				}

			})

		}
	</script>
	<!-- <script type="text/javascript">
		function onContact(){
			alert("hi")
			$.ajax({
				type:"GET",
				url:"user/contact",
				success: function(data){
					
				}
			})
		}
	</script> -->
</body>
</html>