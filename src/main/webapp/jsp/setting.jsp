<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Settings</title>
<%@include file="cdn.jsp"%>

</head>
<body>

	<header>
		<div class="top-bar_sub_w3layouts container-fluid">
			<div class="row">
				<div class="col-md-12 logo text-left" style="z-index: 99999;">

					<a class="navbar-brand" href="">
						<h2>Settings</h2>

					</a>

				</div>

			</div>
		</div>
	</header>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-4">
				<%@include file="sidebar.jsp"%>
			</div>
			<div class="col-md-8">
				<h1>Settings</h1>
				<div class="row">


					<div class="col-md-3"  style="padding-left: 63px;">
					
						<div class="card" style="width: 18rem;">
							
							<div class="card-body">
								<h5 class="card-title">Upload Contacts</h5>
								<p class="card-text">Upload large size contact file Like CSV</p>
								<a href="/user/contactupload" class="btn btn-primary">Click Here</a>
							</div>
						</div>
						
						
					</div>
					

				</div>
			</div>

		</div>
	</div>

</body>
</html>