import React from 'react';
import AdminFooter from '../components/AdminFooter/AdminFooter';
import AdminTable from '../components/AdminTable/AdminTable';
import AdminTitle from '../components/AdminTitle/AdminTitle';

const ManageComment = (props) => {
        return (
                <div>
                        <AdminTitle />
                        <AdminTable />
                        <AdminFooter />
                </div>
        );
}

export default ManageComment;