<?php

namespace App\Http\Controllers\app;

use App\Http\Controllers\Controller;
use App\Models\Baner;
use Illuminate\Http\Request;

class BanerController extends Controller
{
    public function index(){
        $results = [];
        foreach (Baner::latest('id')->paginate() as $baner) {
            $results[] = [
                'id' => $baner->id,
                'title' => $baner->title,
                'image' => $baner->image,
            ];
        }
        if ($results != null) {
            return ['result' => $results];
        }else{
            return ['result'=> 'not data'];
        }
    }
}
