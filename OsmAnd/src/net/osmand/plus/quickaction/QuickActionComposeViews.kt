package net.osmand.plus.quickaction

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.osmand.plus.R

@Composable
fun QuickActionTheme(
	darkTheme: Boolean = false,
	content: @Composable () -> Unit
) {
	val colorScheme = if (darkTheme) {
		darkColorScheme(
			primary = Color(0xFF8AB4F8),
			background = Color(0xFF1C1B1F),
			surface = Color(0xFF303134),
			onPrimary = Color(0xFF13171A),
			onBackground = Color(0xFFE8EAED),
			onSurface = Color(0xFFE8EAED)
		)
	} else {
		lightColorScheme(
			primary = Color(0xFF1A73E8),
			background = Color(0xFFF8F9FA),
			surface = Color(0xFFFFFFFF),
			onPrimary = Color(0xFFFFFFFF),
			onBackground = Color(0xFF1A1A1A),
			onSurface = Color(0xFF1A1A1A)
		)
	}

	MaterialTheme(
		colorScheme = colorScheme,
		content = content
	)
}

@Composable
fun QuickActionItemRow(
	title: String,
	category: String,
	onClick: () -> Unit,
	onEditClick: () -> Unit,
	onDeleteClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 6.dp, horizontal = 12.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 2.dp
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = title,
					fontSize = 16.sp,
					fontWeight = FontWeight.SemiBold,
					color = MaterialTheme.colorScheme.onSurface
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = category,
					fontSize = 12.sp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
				)
			}
			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				IconButton(
					onClick = onEditClick,
					modifier = Modifier.size(36.dp)
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_action_edit_dark),
						contentDescription = "Edit Action",
						tint = MaterialTheme.colorScheme.primary
					)
				}
				IconButton(
					onClick = onDeleteClick,
					modifier = Modifier.size(36.dp)
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_action_remove_dark),
						contentDescription = "Delete Action",
						tint = MaterialTheme.colorScheme.error
					)
				}
			}
		}
	}
}

@Composable
fun QuickActionPreviewPanel(
	actions: List<QuickAction>,
	onActionClick: (QuickAction) -> Unit,
	modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp),
		shape = RoundedCornerShape(24.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 8.dp
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = "Quick Actions",
				fontWeight = FontWeight.Bold,
				fontSize = 18.sp,
				color = MaterialTheme.colorScheme.onSurface
			)
			Spacer(modifier = Modifier.height(12.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				actions.take(3).forEach { action ->
					QuickActionPreviewButton(
						action = action,
						onClick = { onActionClick(action) }
					)
				}
			}
		}
	}
}

@Composable
fun QuickActionPreviewButton(
	action: QuickAction,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current
	val actionName = action.getName(context) ?: "Action"
	Column(
		modifier = modifier
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() }
			.padding(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier
				.size(48.dp)
				.background(
					color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
					shape = RoundedCornerShape(16.dp)
				),
			contentAlignment = Alignment.Center
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_action_plus),
				contentDescription = actionName,
				tint = MaterialTheme.colorScheme.primary,
				modifier = Modifier.size(24.dp)
			)
		}
		Spacer(modifier = Modifier.height(6.dp))
		Text(
			text = actionName,
			fontSize = 11.sp,
			color = MaterialTheme.colorScheme.onSurface
		)
	}
}

@Composable
fun QuickActionSelectionScreen(
	actions: List<QuickAction>,
	onActionClick: (QuickAction) -> Unit,
	onEditAction: (QuickAction) -> Unit,
	onDeleteAction: (QuickAction) -> Unit,
	onBackClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current
	Surface(
		modifier = modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(56.dp)
					.background(MaterialTheme.colorScheme.surface)
					.padding(horizontal = 16.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(onClick = onBackClick) {
					Icon(
						painter = painterResource(id = R.drawable.ic_arrow_back),
						contentDescription = "Back",
						tint = MaterialTheme.colorScheme.onSurface
					)
				}
				Spacer(modifier = Modifier.width(16.dp))
				Text(
					text = "Manage Quick Actions",
					fontSize = 18.sp,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface
				)
			}
			LazyColumn(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f),
				contentPadding = PaddingValues(vertical = 12.dp)
			) {
				items(actions) { action ->
					val categoryName = action.actionType?.let { getCategoryName(it.category, context) } ?: "General"
					QuickActionItemRow(
						title = action.getName(context) ?: "Untitled Action",
						category = categoryName,
						onClick = { onActionClick(action) },
						onEditClick = { onEditAction(action) },
						onDeleteClick = { onDeleteAction(action) }
					)
				}
			}
		}
	}
}

private fun getCategoryName(categoryId: Int, context: Context): String {
	val resId = when (categoryId) {
		0 -> R.string.quick_action_add_create_items
		1 -> R.string.quick_action_add_configure_map
		2 -> R.string.shared_string_navigation
		3 -> R.string.map_widget_config
		4 -> R.string.shared_string_settings
		5 -> R.string.key_event_category_map_interactions
		6 -> R.string.shared_string_my_places
		7 -> R.string.shared_string_interface
		else -> R.string.shared_string_settings
	}
	return context.getString(resId)
}
