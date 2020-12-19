<?php

namespace App\Http\Controllers\app;

use App\Http\Controllers\Controller;
use App\Models\News;
use Hekmatinasser\Verta\Verta;
use Illuminate\Http\Request;

class NewsController extends Controller
{
    public function index()
    {
        $results = News::latest('id')->paginate() ;
       
        if ($results != null) {
            return $results;
        }else{
            return ['data'=> 'not data'];
        }
    }
    

}
