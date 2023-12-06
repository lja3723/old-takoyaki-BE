window.addEventListener('DOMContentLoaded', async()=>{
        await fetchByPost("/user/oauth/login/kakao"+window.location.search,
            convertQueryStringToJson(),
            (response) => {
                console.log(response);
                // window.close();
            },
            () => {},
        );
    }
)