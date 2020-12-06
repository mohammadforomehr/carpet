<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\post;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class PostController extends Controller
{
    public function news()
    {
        return post::latest('id')->paginate(7);
    }
    Public function amazing(){
    
       return post::where('amazing', '=', 'true')->latest('id')->paginate(7);
        
    }
     Public function related($id){
       return post::where('id_category', '=', $id)->latest('id')->paginate(7);
    }
 public function detail($id){
        return post::find($id);
        
    }
    Public function search($field,$value){
        $results = post::where($field, 'like', "%".$value."%")->latest('id')->paginate(7);
        if ($results != null) {
         return $results;
        }else{
            return ['result'=> 'not data'];
        }
    }
}
