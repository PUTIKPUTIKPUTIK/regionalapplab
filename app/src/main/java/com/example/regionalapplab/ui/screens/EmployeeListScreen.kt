package com.example.regionalapplab.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.initializer
import com.example.regionalapplab.data.entity.Employee
import com.example.regionalapplab.data.entity.Region
import com.example.regionalapplab.data.entity.Territory
import com.example.regionalapplab.ui.viewmodel.EmployeeViewModel
import com.example.regionalapplab.ui.viewmodel.RegionViewModel
import com.example.regionalapplab.ui.viewmodel.TerritoryViewModel
import androidx.lifecycle.viewmodel.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListScreen() {
    val context = LocalContext.current.applicationContext as android.app.Application

    val employeeViewModel: EmployeeViewModel = viewModel()
    val regionViewModel: RegionViewModel = viewModel(
        factory = viewModelFactory {
            initializer { RegionViewModel(context) }
        }
    )
    val territoryViewModel: TerritoryViewModel = viewModel(
        factory = viewModelFactory {
            initializer { TerritoryViewModel(context) }
        }
    )

    var selectedEmployee by remember { mutableStateOf<Employee?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    val employees = employeeViewModel.employees
    val regions = listOf("Все", "Регион А", "Регион Б", "Регион В")

    var selectedRegion by remember { mutableStateOf("Все") }
    var regionExpanded by remember { mutableStateOf(false) }

    val filteredEmployees = if (selectedRegion == "Все") employees
    else employees.filter { it.region == selectedRegion }

    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {

        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Добавить сотрудника") }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = regionExpanded,
                    onExpandedChange = { regionExpanded = !regionExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedRegion,
                        onValueChange = {},
                        label = { Text("Фильтр по региону") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(regionExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = regionExpanded,
                        onDismissRequest = { regionExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        regions.forEach { region ->
                            DropdownMenuItem(
                                text = { Text(region) },
                                onClick = {
                                    selectedRegion = region
                                    regionExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(filteredEmployees) { employee ->
                EmployeeItem(
                    employee = employee,
                    onDelete = { employeeViewModel.delete(it) },
                    onEdit = { selectedEmployee = it }
                )
            }
        }
    }

    if (showAddDialog) {
        EmployeeDialog(
            onDismiss = { showAddDialog = false },
            onSave = {
                employeeViewModel.add(it)
                showAddDialog = false
            }
        )
    }

    selectedEmployee?.let { employee ->
        EmployeeDialog(
            employee = employee,
            onDismiss = { selectedEmployee = null },
            onSave = {
                employeeViewModel.update(it)
                selectedEmployee = null
            }
        )
    }
}

@Composable
fun EmployeeItem(
    employee: Employee,
    onDelete: (Employee) -> Unit,
    onEdit: (Employee) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEdit(employee) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${employee.lastName} ${employee.firstName} ${employee.secondName}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${employee.title} | ${employee.city}, ${employee.region}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(employee.email, style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = { onDelete(employee) }) { Text("Удалить") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDialog(
    employee: Employee? = null,
    onDismiss: () -> Unit,
    onSave: (Employee) -> Unit
) {
    // --- Хардкодим регионы и города ---
    val regions = listOf(
        Region(1, "Регион А"),
        Region(2, "Регион Б"),
        Region(3, "Регион В")
    )
    val territories = listOf(
        Territory(1, "Город A1", 1),
        Territory(2, "Город A2", 1),
        Territory(3, "Город A3", 1),
        Territory(4, "Город A4", 1),
        Territory(5, "Город A5", 1),
        Territory(6, "Город Б1", 2),
        Territory(7, "Город Б2", 2),
        Territory(8, "Город Б3", 2),
        Territory(9, "Город Б4", 2),
        Territory(10, "Город Б5", 2),
        Territory(11, "Город В1", 3),
        Territory(12, "Город В2", 3),
        Territory(13, "Город В3", 3),
        Territory(14, "Город В4", 3),
        Territory(15, "Город В5", 3)
    )

    // --- Состояния полей ---
    var firstName by remember { mutableStateOf(employee?.firstName ?: "") }
    var lastName by remember { mutableStateOf(employee?.lastName ?: "") }
    var secondName by remember { mutableStateOf(employee?.secondName ?: "") }
    var title by remember { mutableStateOf(employee?.title ?: "") }
    var birthDay by remember { mutableStateOf(employee?.birthDay ?: "") }
    var address by remember { mutableStateOf(employee?.address ?: "") }
    var phone by remember { mutableStateOf(employee?.phone ?: "") }
    var email by remember { mutableStateOf(employee?.email ?: "") }

    var selectedRegion by remember { mutableStateOf(employee?.region ?: "") }
    var selectedCity by remember { mutableStateOf(employee?.city ?: "") }

    var regionExpanded by remember { mutableStateOf(false) }
    var cityExpanded by remember { mutableStateOf(false) }

    val filteredCities = if (selectedRegion.isNotEmpty()) {
        territories.filter { it.regionId == regions.find { r -> r.regionDescription == selectedRegion }?.id }
    } else emptyList()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (employee == null) "Добавить сотрудника" else "Редактировать сотрудника") },
        text = {
            val scrollState = rememberScrollState()
            Column (modifier = Modifier.padding(top = 16.dp).verticalScroll((scrollState))) {
                // --- Поля сотрудника ---
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Фамилия") }, isError = lastName.isBlank())
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Имя") }, isError = firstName.isBlank())
                OutlinedTextField(value = secondName, onValueChange = { secondName = it }, label = { Text("Отчество") }, isError = secondName.isBlank())
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Должность") }, isError = title.isBlank())
                OutlinedTextField(
                    value = birthDay,
                    onValueChange = { birthDay = it },
                    label = { Text("Дата рождения") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = DateVisualTransformation(),
                    isError = birthDay.isBlank()
                )
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Адрес") })
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Телефон") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    visualTransformation = PhoneNumberVisualTransformation(),
                    isError = phone.isBlank()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = email.isBlank()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- Dropdown для региона ---
                ExposedDropdownMenuBox(
                    expanded = regionExpanded,
                    onExpandedChange = { regionExpanded = !regionExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRegion,
                        onValueChange = {},
                        label = { Text("Регион") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        isError = selectedRegion.isBlank()
                    )
                    DropdownMenu(
                        expanded = regionExpanded,
                        onDismissRequest = { regionExpanded = false },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        regions.forEach { region ->
                            DropdownMenuItem(
                                text = { Text(region.regionDescription) },
                                onClick = {
                                    selectedRegion = region.regionDescription
                                    selectedCity = ""
                                    regionExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // --- Dropdown для города ---
                ExposedDropdownMenuBox(
                    expanded = cityExpanded && filteredCities.isNotEmpty(),
                    onExpandedChange = { cityExpanded = !cityExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCity,
                        onValueChange = {},
                        label = { Text("Город") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        enabled = selectedRegion.isNotEmpty(),
                        isError = selectedCity.isBlank()
                    )
                    DropdownMenu(
                        expanded = cityExpanded,
                        onDismissRequest = { cityExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        filteredCities.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(city.description) },
                                onClick = {
                                    selectedCity = city.description
                                    cityExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (firstName.isBlank() || lastName.isBlank() || secondName.isBlank() ||
                    title.isBlank() || birthDay.isBlank() || address.isBlank() ||
                    phone.isBlank() || email.isBlank() || selectedRegion.isBlank() || selectedCity.isBlank()
                ) {
                    return@TextButton
                }
                onSave(
                    Employee(
                        id = employee?.id ?: 0,
                        firstName = firstName,
                        lastName = lastName,
                        secondName = secondName,
                        title = title,
                        birthDay = birthDay,
                        address = address,
                        city = selectedCity,
                        region = selectedRegion,
                        phone = phone,
                        email = email
                    )
                )
            }) { Text("Сохранить") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Отмена") } }
    )
}

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(8)
        val out = StringBuilder()
        trimmed.forEachIndexed { index, c ->
            out.append(c)
            if (index == 1 || index == 3) out.append(".")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 8) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                if (offset <= 10) return offset - 2
                return 8
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(10)
        val builder = StringBuilder("+7 ")

        digits.forEachIndexed { index, c ->
            when (index) {
                0 -> builder.append("($c")
                1, 2 -> builder.append(c)
                3 -> builder.append("${c}) ")
                4, 5 -> builder.append(c)
                6 -> builder.append("${c}-")
                7 -> builder.append(c)
                8 -> builder.append("${c}-")
                9 -> builder.append(c)
            }
        }

        val transformedText = builder.toString()

        val originalToTransformedMap = IntArray(digits.length + 1) { i ->
            var pos = 3 // после "+7 "
            if (i > 0) pos += minOf(i, 3) // код региона
            if (i > 3) pos += 2 // после скобок и пробела
            if (i > 5) pos += 1 // после тире
            if (i > 7) pos += 1 // после второго тире
            minOf(pos, transformedText.length) // не выходим за границы
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset in originalToTransformedMap.indices) originalToTransformedMap[offset] else transformedText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                // ищем максимальный индекс оригинала, который <= offset
                for (i in originalToTransformedMap.indices.reversed()) {
                    if (originalToTransformedMap[i] <= offset) return i
                }
                return 0
            }
        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}


