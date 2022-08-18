import React from 'react';
import MainButtons from '../components/button/main_buttons';
import { useNavigate } from 'react-router-dom';
import Nav from "../components/Nav/Nav";
import Footer from '../components/Footer/Footer';

const Menu = () => {
    let navigate = useNavigate();

    return (
        <>
            <Nav navigate={navigate} />
            <MainButtons />
            <Footer />
        </>
    );    
};

export default Menu;