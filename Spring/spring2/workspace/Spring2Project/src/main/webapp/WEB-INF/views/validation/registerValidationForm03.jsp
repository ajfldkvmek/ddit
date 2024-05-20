<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registerForm01</title>
</head>
<body>

	<!-- 
		
		[입력값 검증 결과 테스트 시나리오]
		1. 유효한 데이터 입력
		2. 우편번호 빈값으로 입력
		3. 카드번호 빈값으로 입력
		4. 카드유효일 과거일로 입력
	
	 -->
	
	<form:form modelAttribute="member" method="post" action="/validation/result3">
		<table border="1">
			
			<tr>
				<td>생년월일</td>
				<td>
					<form:input path="dateOfBirth" type="date"/>
					<font color="red">
						<form:errors path="dateOfBirth"/>
					</font>
				</td>
			</tr>
			
			<tr>
				<td>우편번호</td>
				<td>
					<form:input path="address.postCode" />
					<font color="red">
						<form:errors path="address.postCode"/>
					</font>
				</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>
					<form:input path="address.location" />
					<font color="red">
						<form:errors path="address.location"/>
					</font>
				</td>
			</tr>
			
			
			<tr>
				<td>카드1 번호</td>
				<td>
					<form:input path="cardList[0].no" />
					<font color="red">
						<form:errors path="cardList[0].no"/>
					</font>
				</td>
			</tr>
			
			<tr>
				<td>카드1 유효날짜</td>
				<td>
					<form:input path="cardList[0].validMonth" />
					<font color="red">
						<form:errors path="cardList[0].validMonth"/>
					</font>
				</td>
			</tr>
			
			<tr>
				<td>카드2 번호</td>
				<td>
					<form:input path="cardList[1].no" />
					<font color="red">
						<form:errors path="cardList[1].no"/>
					</font>
				</td>
			</tr>
			
			<tr>
				<td>카드2 유효날짜</td>
				<td>
					<form:input path="cardList[1].validMonth" />
					<font color="red">
						<form:errors path="cardList[1].validMonth"/>
					</font>
				</td>
			</tr>
			
			
		
			
			
			
		</table>
		<form:button name="register">등록</form:button>
	</form:form>
	

</body>
</html>