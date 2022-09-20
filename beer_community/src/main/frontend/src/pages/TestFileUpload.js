import React from "react";
import { useNavigate } from "react-router-dom";

const TestFileUpload = () => {
    const ButtonElement = document.querySelector('#Button');
    const FileElement = document.querySelector('#File');

    const getData = async () => {
        try{
            const formData = new FormData();
            formData.append("user", "Pyo");
            formData.append("content", "this is content");
            for(let i = 0; i < FileElement.files.length; i++){
                formData.append("files", FileElement.files[i]);
            }
            const url = 'http://localhost:8080/board';

            const response = await axios.post(url, formData);
            console.log(response)
        }
        catch (error){
            console.error(error);
        }
    }

//    ButtonElement.addEventListener('click', getData);
    return (
        <>
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>파일 보내기 예제</title>
            <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
          </head>
          <body>
            <input type="file" id="File" name="files" multiple="multiple"/>
            <button id="Button" onClick={getData}>전송</button>

            <script src="./index.js"></script>
          </body>
        </>
    );
}
export default TestFileUpload;
