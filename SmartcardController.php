<?php

namespace App\Http\Controllers;

require_once("Java.inc");

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Session;

class SmartcardController extends Controller
{
    protected $Java;
    protected $algorithm = 'SHA1withRSA';

    public function __construct()
    {
        $this->Java = new \Java("forphp.Forphp");
    }


    public function getToken()
    {
        $token= $this->Java->getToken();
        Session::put('token', $token);
    }


    public function index()
    {
        return view('smartcard.index');
    }


    public function login(Request $request)
    {
        $this->getToken();
        $password = $request->get('password');
        if(java_values($this->Java->PHPlogin($token = Session::get('token'),$password)))
        {
            $login = '登入成功';
            return view('smartcard.login',compact('login','token'));
        }
        $login = '登入失敗';
        return view('smartcard.login',compact('login','token'));
    }


    public function logout()
    {
        if (Session::has('token'))
        {
            if(java_values($this->Java->PHPlogout($token = Session::get('token'))))
            {
                $logout = '登出成功';
                Session::forget('token');
                return view('smartcard.logout',compact('logout','token'));
            }
            $logout = '登出失敗';
            return view('smartcard.logout',compact('logout','token'));
        }
        return back();
    }


    public function sign()
    {
        if (Session::has('token'))
        {
            if($sign = java_values($this->Java->PHPSign($token = Session::get('token'),$this->algorithm)))
            {
                return view('smartcard.sign',compact('sign','token'));
            }
            else
            {
                return view('smartcard.sign',compact('sign','token'));
            }
        }
        return back();
    }


    public function cert()
    {
        if (Session::has('token'))
        {
            $cert = java_values($this->Java->getCertAll($token = Session::get('token')));
            return view('smartcard.cert', compact('cert', 'token'));
        }
        return back();
    }


    public function phpencrypt()
    {
        if (Session::has('token'))
        {
            $phpencrypt = java_values($this->Java->PHPEncrypt($token = Session::get('token')));
            return view('smartcard.encrypt',compact('phpencrypt','token'));
        }
        return back();
    }


    public function ocsp()
    {
        if (Session::has('token'))
        {
            $ocsp = java_values($this->Java->PHPOCSP($token = Session::get('token')));
            return view('smartcard.ocsp',compact('ocsp','token'));
        }
        return back();
    }
}
