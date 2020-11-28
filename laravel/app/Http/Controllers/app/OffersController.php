<?php

namespace App\Http\Controllers\app;

use App\Http\Controllers\Controller;
use App\Models\offers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class OffersController extends Controller
{

public function store(Request $request){

    $result=offers::create(
        [
            'name' => $request['name'],
            'email' => $request['email'],
            'phone' => $request['phone'],
            'addressweb' => $request['addressweb'],
            'message' => $request['message'],
        ]
    );
    return ['result'=>$result];
}
}
