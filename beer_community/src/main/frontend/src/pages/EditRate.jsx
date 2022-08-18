import React from 'react';
import { useNavigate } from 'react-router-dom';
import Nav from '../components/Nav/Nav';
import { useLocation } from 'react-router-dom';
import EditComment from './../components/editComment/editComment';

const EditRate = (props) => {
        let navigate = useNavigate();
        const location = useLocation();
        const id = location.state.id;
        const commentId = location.state.commentId;
        console.log(id);

        return (
                <div>
                        <Nav navigate={navigate}/>
                        <EditComment id={id} commentId={commentId}/>
                </div>
        );
};

export default EditRate;