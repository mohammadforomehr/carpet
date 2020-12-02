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
        $results = [];
        foreach (News::latest('id')->paginate() as $news) {
            $results[] = [
                'id' => $news->id,
                'title' => $news->title,
                'caption' => $news->caption,
                'image' => $news->image,
                'create_date'=>Verta::instance($news->created_at)->format('Y/n/j')
            ];
        }
        if ($results != null) {
            return response()->json([
                'result' => $results
            ]);
        }else{
            return ['result'=> 'not data'];
        }
    }
}
