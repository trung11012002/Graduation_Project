import React, { useContext, useState } from 'react';
import './Form.css';
import { Link, useNavigate } from 'react-router-dom';
import { login } from '../../apis/auth';

import { AuthContextProvider } from '../../contexts/AuthContext';
import { MessageContextProvider } from '../../contexts/MessageContext';

interface IFormLogin {
    username: string | undefined
    password: string | undefined
}

const Form = () => {
    const auth = useContext(AuthContextProvider);
    const message = useContext(MessageContextProvider);
    const setUserState = auth?.setUserState
    const success = message?.success
    const error = message?.error

    const [data, setData] = useState<IFormLogin>({
        username: undefined,
        password: undefined
    })
    
    const navigate = useNavigate()
    const submitFormLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const res = await login(data)
        if (res?.code === 1000) {
            localStorage.setItem('websocketConnected', "false");
            setUserState?.({
                isLogin: true,
                user: res.data,
                tokenAccess: res.data.token,
                tokenRefresh: res.data.tokenRefresh
            })
            localStorage.setItem('tokenAccess', res.data.token)
            localStorage.setItem('tokenRefresh', res.data.tokenRefresh)
            auth?.autoLogin();
            success("Đăng nhập thành công")
            res.data.role === "SUPER_ADMIN" ?
                navigate('/super-admin/theater-list')
                :
                res.data.role === "ADMIN" ?
                    navigate('/admin/movie-schedule')
                    :
                    navigate('/')
        }else{
            error(res?.msg)
        }
    };

    const navigateHome = async (e: any) => {
        e.preventDefault();
        navigate('/')
    };

    const changeInfor = (e: React.ChangeEvent<HTMLInputElement>) => {
        setData({
            ...data,
            [e.target.name]: e.target.value
        })
    }

    return (
        <div className='Form'>
            <div>

                <div className='login-form'>
                    <h1 onClick={navigateHome}>ABSOLUTE CINEMA</h1>
                    <form onSubmit={submitFormLogin}>
                        <div>
                            <label htmlFor="username">Tên đăng nhập:</label>
                            <input
                                id='username'
                                type="username"
                                name='username'
                                placeholder='username'
                                onChange={changeInfor}
                            />
                        </div>
                        <div>
                            <label htmlFor="password">Mật khẩu:</label>
                            <input
                                id='password'
                                type="password"
                                name='password'
                                placeholder='******'
                                onChange={changeInfor}
                            />
                        </div>
                        <span onClick={() => navigate("/forgot-password")}>Quên mật khẩu???</span>
                        <button>Đăng nhập</button>
                    </form>

                    <p>Bạn chưa có tài khoản <Link to='/register'>Đăng Ký</Link></p>
                </div>

                <div className='img-loginform'>
                    <img className="stylish-img" src={"https://res.cloudinary.com/dbym9b0xi/image/upload/v1732933898/d8f685cc-81cb-480e-879e-72a58649454c_oejrgi.jpg"} alt="" />
                </div>
            </div>
        </div>
    )
}

export default Form