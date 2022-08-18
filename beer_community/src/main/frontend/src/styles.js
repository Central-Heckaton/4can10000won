import reset from "styled-reset";
import { createGlobalStyle } from "styled-components";
export const GlobalStyle = createGlobalStyle`
${reset}

    body{ // 폰트 종류, 배경색

        font-family: 'Noto Sans CJK KR';
        font-style: normal;
        font-weight: 500; 
        display: flex;
        justify-content: center;
    }
    :root{ //색, 폰트 설정, radius 설정
        --button-color:#AAE8BE;
        --button-non-color:rgb(113 112 112);
        --input-color:#ddd;
        --background-color:rgb(255 255 255);

        --font-large: 24px;
        --font-medium: 15px;
        --font-small: 12px;
    }
    button {
        background-color: var(--button-color);
        cursor: pointer;
        border: none;
        outline: none;
    }
    a:link { 
        text-decoration: none;
        color: black;
    }
 
    a:visited { 
        text-decoration: none;
        color: black;
    }
`;
