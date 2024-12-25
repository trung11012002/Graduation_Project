import { useContext } from 'react';
import './Header.css';
import { Link, useNavigate } from 'react-router-dom';

import { AuthContextProvider } from '../../contexts/AuthContext';
import { Button, Dropdown, MenuProps, Modal, Space } from 'antd';
import { CloseOutlined, ExclamationCircleFilled } from '@ant-design/icons';
import Avatar from '../Avatar/Avatar';
import NotificationComponent from "./Notification";

const { confirm } = Modal;

const Header = () => {
  const auth = useContext(AuthContextProvider)
  const user = auth?.userState
  const navigate = useNavigate();
  const navigateLoginForm = (e: any) => {
    e.preventDefault();
    navigate('/login')
  };

  const showLogoutModal = () => {
    confirm({
      title: "Bạn có chắc chắn đăng xuất???",
      icon: <ExclamationCircleFilled />,
      okText: "Đăng xuất",
      // okType: 'danger',
      closeIcon: <CloseOutlined />,
      cancelText: "Ở lại",
      onOk() {
        auth?.logout()
        navigate('/login')
      },
      onCancel() { },
    });
  };

  const url_information = user?.user?.role.name === "ADMIN" ? "/admin/update-information" : "/super-admin/update-information"

  const items: MenuProps['items'] = [
    {
      key: '3',
      label: (
        <Link to={url_information}>{`Thông tin cá nhân -->`}</Link>
      ),
    },
    {
      key: '',
      label: (
        <Button onClick={showLogoutModal}>Đăng xuất</Button>
      ),
    },
  ];

  return (
      <header className='header'>
        <div className='header-container'>
          <div className='logo'>
            <img src="https://res.cloudinary.com/dme0cssq0/image/upload/v1732725928/film-booking-high-resolution-logo-transparent_5_mksz3f.webp" alt="Logo"/>
          </div>
          <div className='auth-section'>
            {user?.isLogin ? (
                <>
                  <span className='welcome-text'>Xin chào {user.user?.username}</span>
                  <Dropdown
                      menu={{
                        items: [
                          {key: '1', label: <Link to='/update-information'>Thông tin cá nhân</Link>},
                          {key: '2', label: <Button onClick={showLogoutModal}>Đăng xuất</Button>}
                        ]
                      }}
                      trigger={['click']}
                  >
                    <div className='avatar-container'>
                      <Avatar width='40px'/>
                    </div>
                  </Dropdown>
                </>
            ) : (
                <div className='auth-buttons'>
                  <button className='login-btn' onClick={navigateLoginForm}>Đăng nhập</button>
                  <button className='register-btn' onClick={() => navigate('/register')}>Đăng ký</button>
                </div>
            )}
          </div>
        </div>
      </header>
  )
}

export default Header