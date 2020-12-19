<?php

namespace App\Http\Controllers\app;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\about;
class AboutController extends Controller
{
    public function index(){
        return about::first();
    }
}
