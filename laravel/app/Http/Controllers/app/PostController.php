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
        return post::latest('id')->paginate(6);
    }
    Public function amazing(){
    
       return post::where('amazing', '=', 'true')->latest('id')->paginate(6);
        
    }
     Public function related($id){
       return post::where('id_category', '=', $id)->latest('id')->paginate(6);
    }
 public function detail($id){
        return post::find($id);
        
    }
    Public function search($value){
        // $results = post::whereIn('title', 'like', "%".$value."%")->whereIn('code_carpet', 'like', "%".$value."%")->latest('id')->paginate(7);
        $results =post::whereRaw(" (`title` like ? or `code_carpet` like ? or `color` like ? or `Plan` like ? or `shoulder` like ?) ",["%".$value."%","%".$value."%","%".$value."%","%".$value."%","%".$value."%"])->latest('id')->paginate(6);
        if ($results != null) {
         return $results;
        }else{
            return ['data'=> 'not data'];
        }
    }
}
