package ir.dehghanifard.kotlin_mvi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.dehghanifard.kotlin_mvi.models.UiComponent
import ir.dehghanifard.kotlin_mvi.ui.theme.Kotlin_mviTheme
import ir.dehghanifard.kotlin_mvi.viewmodels.PostViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<PostViewModel>()
            Kotlin_mviTheme {
                val state by viewModel.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)){
                        items(state.posts.size) { index ->
                                Card(
                                    modifier = Modifier.padding(bottom = 10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.LightGray
                                    )
                                ) {

                                    Column(modifier = Modifier.padding(15.dp)) {
                                        Text(text = state.posts[index].title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = state.posts[index].body)
                                    }
                                    Divider()
                                    Text(text = "PostId : ${state.posts[index].id}",modifier = Modifier.padding(15.dp))
                            }
                        }

                    }
                    
                    if (state.progressBar) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                        }
                    }

                    viewModel.collectSideEffect { it ->
                        when(it){
                            is UiComponent.Toast -> {
                                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        }
    }
}

