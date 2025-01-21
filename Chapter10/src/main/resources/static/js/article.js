// 주석다는 방법 -> 삭제 기능을 구현
const deleteButton = document.getElementById('delete-btn');

// if (deleteButton) {
//     deleteButton.addEventListener('click', event => {
//         let id = document.getElementById('article-id').value;
//         fetch(`/api/articles/${id}`, {
//             method: 'DELETE'
//         })
//         .then(() => {
//             alert('삭제가 완료되었습니다.');
//             location.replace('/articles')
//         });
//     });
// }   // 이제 이게 article.html에서 동작할 수 있도록 작성을 할 예정입니다.

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success(){
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }
        function fail(){
            alert('삭제에 실패했습니다.');
            location.replace('/articles');
        }
        //표현식 사용할때는 백틱 `` 사용해야한다.
        httpRequest('DELETE',`/api/articles/${id}`,success,fail)
    })
}

// 수정 기능
// 1. id가 modify-btn 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

// if (modifyButton) {
//     // 2. 클릭 이벤트가 감지되면 수정 API 요청
//     modifyButton.addEventListener('click', event => {
//         let params = new URLSearchParams(location.search);
//         let id = params.get('id');
//
//         fetch(`/api/articles/${id}`, {
//             method: 'PUT',
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify({
//                 title: document.getElementById('title').value,
//                 content: document.getElementById('content').value
//             })
//         })
//         .then(() => {
//             alert('수정이 완료되었습니다.');
//             location.replace(`/articles/${id}`);
//         });
//     });
// }

if(modifyButton){
    modifyButton.addEventListener('click',event=>{
        let params = new URLSearchParams(location.search);
        let id = params.get('id');
        //기존에 썼던 글을 다시 불러오는 과정
        body=JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })
        function success(){
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`); //고친거 확인
        }
        function fail(){
            alert('수정에 실패했습니다.');
            location.replace(`/articles/${id}`);
        }
        httpRequest('PUT',`/api/articles/${id}`,body,success,fail)
    })
}

// 등록 기능
// 1. id가 create-btn인 엘리먼트
const createButton = document.getElementById("create-btn");

// if (createButton) {
//     // 2. 클릭 이벤트가 감지되면 생성 API 요청
//     createButton.addEventListener("click", (event) => {
//         fetch("/api/articles", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify({
//                 title: document.getElementById("title").value,
//                 content: document.getElementById("content").value,
//             }),
//         }).then(() => {
//             alert("등록 완료되었습니다.");
//             location.replace("/articles");
//         });
//     });
// }

if (createButton){
    //등록 버튼을 클릭하면 /api/articles로 요청을 보낸다.
    createButton.addEventListener('click',event=>{
        body=JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success(){
            alert('등록이 완료되었습니다.');
            location.replace('/articles');
        }
        function fail(){
            alert('등록에 실패했습니다.');
            location.replace('/articles');
        }
        //함수를 인수로 받는다
        //기존의 요청을 깔끔하게 함수로 떼어버렸다.
        httpRequest('POST','/api/articles',body,success,fail)
    })
}

//http 요청을 보내는 함수, 요청을 받는 함수 작성하기
function getCookie(key){
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function(item){
        item=item.replace(' ','');
        var dict = item.split('=');
        if(key===dict[0]){
            result=dict[1];
            return true;
        }
    });
    return result;
}

function httpRequest(method,url,body,success,fail){
    fetch(url,{
        method:method,
        headers:{
            authorization: 'Bearer'+localStorage.getItem('access_token'),
            'Content-Type':'application/json',
        },
        body:body,
    }).then(response=>{
        if(response.status===200||response.status===201){
            return success();}
        const refresh_token = getCookie('refresh_token');
        if(response.status===401&&refresh_token){
            //토큰을 다시 받는다.
            fetch('api/token',{
                method:'POST',
                headers:{
                    Authorization: 'Bearer'+localStorage.getItem('access_token'),
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    refresh_token:getCookie('refresh_token'),
                }),
            })
                .then(res=>{
                    if(res.ok){
                        return res.json();
                    }
                })
                .then(result=>{ //재발급이 성공하면 로컬 스토리지 값을 새로운 액세스 토큰으로 갱신한다.
                    localStorage.setItem('access_token',result.accessToken);
                    httpRequest(method,url,body,success,fail);
                })
                .catch(error=>fail());
        }else{
            return fail();
        }
    });
}

//글을 수정하거나 삭제할 때 글쓴이가 요구되기 때문에,
//본인 글이 아닌데, 수정 삭제를 시도하는 경우에 예외를 발생시키도록 작성