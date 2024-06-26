<%@page import="meet.vo.MbVO"%>
<%@page import="member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="board.vo.PostVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberVO mv = (MemberVO) session.getAttribute("LOGIN_USER");
    MbVO mb = new MbVO();
	if(mv == null) { %>
		<script>alert("로그인이 필요합니다"); location.href="/nawa/member/signin.jsp"</script>
	<%} 
%>
<%
	
	/* List<BoardAtchFileVO> atchFileList = 
		(List<BoardAtchFileVO>) request.getAttribute("atchFileList"); */
%>

<%@include file="/include/header.jsp"%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>



<style>
#buttonContainer {
padding: 10px;
}
.updateButton{
border: 2px dashed teal;
border-radius: 10px;
background-color: white;
padding: 0px 13px;
font-family: 'Dongle', sans-serif;
font-size: 150%;
}
#updateContainer {
padding: 10px;
}
.row{
justify-content: center
}
</style>
<!-- </head> -->

<!-- <body> -->
<section class="py-4">
<div class="container">
<div class="row">
<div class="col-sm-8 text-left">
<%--   	<form id="updateForm" action="${pageContext.request.contextPath}/board/update.do"  --%>
  	<form id="updateForm" action="./update.do" method="post" enctype="multipart/form-data"
  		onsubmit="convertTextarea()">
  		<input type="hidden" name="postNo" value="<%=mb.getMb_no()%>">
<%--   		<input type="hidden" name="atchFileId" value="<%=pv.getAtchFileId()%>"> --%>
  		
  		<div id="updateContainer" class="form-group">
				<label for="postNo">글번호: </label>
				<input type="text" name="postNo" id="postNo" class="form-control" value="" disabled>
				
				<label for="postWriter">작성자: </label>
				<input type="text" name="postWriter" id="postWriter" class="form-control" value="" disabled>
				
				<label for="postDate">작성일: </label>
				<input type="text" name="postDate" id="postDate" class="form-control" value="" disabled>

				<label for="postCate">분류: </label>
					<select name="postCate" id="postCate" class="form-control">
						<option value="100">잡담</option>
						<option value="999">공지사항</option>
						<option value="200">후기</option>
						<option value="300">기대평</option>
					</select>
				
				<label for="postTitle">제  목: </label>
				<input type="text" name="postTitle" id="postTitle" class="form-control"value="">
				
				<label for="postContent">내용: </label>
				<textarea name="postContent" id="postContent" class="form-control" ></textarea>
				
				<label for="atchOld">기존 첨부파일:</label>
				<div id="atchOld">
				<%-- <%
					if(atchFileList != null) { 
							for(BoardAtchFileVO fileVO : atchFileList) {
				%>
				<a href="#"><%=fileVO.getBoardOriName() %></a>
					<a href="<%=request.getContextPath() %>/fileDownload.do?fileId=<%=fileVO.getBoardFIndex() %>"><%=fileVO.getBoardOriName() %></a>
				<%
					}
				}
				%> --%>
				</div>
				<label for="atchNew">신규 첨부파일: </label>
				<input type="file" id="atchNew" name="atchFile" multiple="multiple"/>
  		</div>
  		
  		<div id="buttonContainer" class="btn-group">
			<button type="button" class="btn btn-dark-soft" onclick="location.href='./list.do'">목록</button>
<%--    			<button type="submit" class="btn btn-primary" onclick="location.href='./update.do?postNo=${pv.getPostNo()}'">edit</button> --%>
   			<button type="submit" class="btn btn-dark-soft">수정</button>
			
			
  		</div>	
 			
	</form>
</div>

</div>
</div>
</section>


<script>
function convertTextarea() {
    // textarea의 내용을 가져옴
    let postContentValue = document.getElementById("postContent").value;

    // \r\n을 <br>로 치환
    let convertedValue = postContentValue.replace(/(\n|\r\n)/g, "<br>");

    // 변환된 값을 다시 textarea에 설정
    document.getElementById("postContent").value = convertedValue;
}
</script>

<%@include file="/include/footer.jsp"%>

</body>
</html>